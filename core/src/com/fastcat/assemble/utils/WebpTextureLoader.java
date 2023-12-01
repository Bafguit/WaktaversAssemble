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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import static com.fastcat.assemble.WakTower.pixmapFactory;

public class WebpTextureLoader extends AsynchronousAssetLoader<Texture, WebpTextureLoader.TextureParameter> {
	public WebpTextureLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	Texture texture;

	@Override
	public void loadAsync (AssetManager manager, String fileName, FileHandle file, TextureParameter parameter) {
		texture = null;
		texture = new Texture(pixmapFactory.createPixmap(file));
		texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
	}

	@Override
	public Texture loadSync (AssetManager manager, String fileName, FileHandle file, TextureParameter parameter) {
		Texture texture = this.texture;
		this.texture = null;
		return texture;
	}

	@Override
	public Array<AssetDescriptor> getDependencies (String fileName, FileHandle file, TextureParameter parameter) {
		return null;
	}

	static public class TextureParameter extends AssetLoaderParameters<Texture> {
	}
}
