package com.fastcat.assemble.handlers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Queue;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class ResourceHandler {
    @Setter
    @Getter
    private boolean started;

    private final Queue<ResourceRequest<?>> queuedRequests;

    private final AssetManager assetManager;

    public ResourceHandler(AssetManager assetManager) {
        this.assetManager = assetManager;
        queuedRequests = new Queue<>();
        started = false;
    }

    /***
     * Request {@link AssetManager} to load that resource
     * <br>
     * @param resourceRequest resource to request
     * @param <R> expected return type of resource
     */
    public <R> void requestResource(ResourceRequest<R> resourceRequest) {
        if (resourceRequest instanceof MultipleResourceRequest<R> m) {
            for (String value : m.resourceNames.values()) {
                assetManager.load(value, resourceRequest.resourceType);
                queuedRequests.addLast(resourceRequest);
            }
            return;
        }
        assetManager.load(resourceRequest.resourceName, resourceRequest.resourceType);
        queuedRequests.addLast(resourceRequest);
    }

    /***
     * Must be called by main {@link ApplicationListener#render()}
     */
    public boolean process() {
        if (assetManager.update(17)) {
            System.out.println("Called process");
            boolean actualProcessing = false;
            while (queuedRequests.notEmpty()) {
                actualProcessing = true;
                ResourceRequest<?> req = queuedRequests.removeFirst();

                if (req instanceof MultipleResourceRequest<?>) {
                    MultipleResourceRequest<?> multipleReq = (MultipleResourceRequest<?>) req;

                    for (Map.Entry<String, String> resourceName :
                            multipleReq.resourceNames.entrySet()) {
                        Object loadedResource = assetManager.get(resourceName.getValue(), req.resourceType);
                        req.callback.onResourceLoaded(loadedResource, resourceName.getKey());
                    }
                    continue;
                }
                Object loadedResource = assetManager.get(req.resourceName, req.resourceType);
                req.callback.onResourceLoaded(loadedResource, req.args);
            }
            assetManager.finishLoading();
            return actualProcessing;
        }
        return false;
    }

    public float getProgress() {
        return assetManager.getProgress();
    }

    public interface ResourceCallback {
        /***
         * Called when {@link AssetManager} completed loading all resources
         * @param resource
         */
        void onResourceLoaded(Object resource, Object... args);
    }

    public static class ResourceRequest<R> {

        @NotNull
        public String resourceName;

        @NotNull
        public final Class<R> resourceType;

        @NotNull
        public final ResourceCallback callback;

        @Nullable
        public Object[] args;

        public ResourceRequest(String resourceName, Class<R> resourceType, ResourceCallback callback, Object... args) {
            this.resourceName = resourceName;
            this.resourceType = resourceType;
            this.callback = callback;
            this.args = args;
        }
    }

    public static class MultipleResourceRequest<R> extends ResourceHandler.ResourceRequest<R> {
        public final HashMap<String, String> resourceNames;

        public MultipleResourceRequest(
                HashMap<String, String> resourceNames, Class<R> resourceType, ResourceHandler.ResourceCallback callback) {
            super(null, resourceType, callback, resourceNames);
            this.resourceNames = resourceNames;
        }
    }
}
