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
import com.badlogic.gdx.utils.JsonValue;
import com.fastcat.assemble.handlers.FileHandler;

import static com.fastcat.assemble.WakTower.pixmapFactory;

public class JsonLoader extends AsynchronousAssetLoader<JsonValue, JsonLoader.JsonParameter> {
	public JsonLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	JsonValue json;

	@Override
	public void loadAsync (AssetManager manager, String fileName, FileHandle file, JsonParameter parameter) {
		json = null;
		json = FileHandler.generateJson(file);
	}

	@Override
	public JsonValue loadSync (AssetManager manager, String fileName, FileHandle file, JsonParameter parameter) {
		JsonValue texture = this.json;
		this.json = null;
		return texture;
	}

	@Override
	public Array<AssetDescriptor> getDependencies (String fileName, FileHandle file, JsonParameter parameter) {
		return null;
	}

	static public class JsonParameter extends AssetLoaderParameters<JsonValue> {
	}
}
