package com.miyuki.baddapple;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class IconPack {

	public static IconPack current;
	public static List<String> foundIconPacks = new ArrayList<String>();
	public static File iconPackFolder = new File("iconpacks");
	
	public ImageIcon fileIcon;
	public ImageIcon folderIcon;
	public HashMap<String, ImageIcon> extIcons;
	
	public IconPack(String rawFile, String filename) {
		extIcons = new HashMap<String, ImageIcon>();

		JSONObject rawObject;
		try {
			rawObject = (JSONObject) JSONValue.parseWithException(rawFile);
		} catch(Exception e) {
			System.err.println("Failed to load theme file: " + filename);
			e.printStackTrace();
			return;
		}
		
		if (rawObject.containsKey("file")) {
			this.fileIcon = Resource.Resize(Resource.GetImage((String)rawObject.get("file")),16);
		}
		
		if (rawObject.containsKey("folder")) {
			this.folderIcon = Resource.Resize(Resource.GetImage((String)rawObject.get("folder")),16);
		}
		
		if (rawObject.containsKey("extensions")) {
			JSONObject extensionsIcons = (JSONObject) rawObject.get("extensions");
			
			@SuppressWarnings("rawtypes")
			Set a = extensionsIcons.keySet();
			
			for (Object k : a) {
				extIcons.put((String)k, Resource.Resize(Resource.GetImage((String)extensionsIcons.get(k)), 16));
			}
		}
	}
	
	public ImageIcon GetExtIcon(String key) {
		if (extIcons.containsKey(key))
			return extIcons.get(key);
		return fileIcon;
	}
	
	public static void LoadIconPacks() {
		if (!iconPackFolder.exists() || !iconPackFolder.isDirectory()) {
			iconPackFolder.mkdir();
		}
		
		File[] files = iconPackFolder.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".json");
			}
		});
		
		for (File f : files) {
			foundIconPacks.add(f.getName());
		}
		
		System.out.println("Loaded " + foundIconPacks.size() + " Icon Packs.");
		current = new IconPack(Resource.GetFile("internal://defaultIconPack.json"), "defaultIconPack.json");
	}
}
