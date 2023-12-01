/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.fastcat.assemble.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;

import static com.fastcat.assemble.WakTower.pixmapFactory;

public class WebpPixmapLoader extends AsynchronousAssetLoader<Pixmap, WebpPixmapLoader.PixmapParameter> {
	public WebpPixmapLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	Pixmap pixmap;

	@Override
	public void loadAsync (AssetManager manager, String fileName, FileHandle file, PixmapParameter parameter) {
		pixmap = null;
		pixmap = pixmapFactory.createPixmap(file);
	}

	@Override
	public Pixmap loadSync (AssetManager manager, String fileName, FileHandle file, PixmapParameter parameter) {
		Pixmap pixmap = this.pixmap;
		this.pixmap = null;
		return pixmap;
	}

	@Override
	public Array<AssetDescriptor> getDependencies (String fileName, FileHandle file, PixmapParameter parameter) {
		return null;
	}

	static public class PixmapParameter extends AssetLoaderParameters<Pixmap> {
	}
}
