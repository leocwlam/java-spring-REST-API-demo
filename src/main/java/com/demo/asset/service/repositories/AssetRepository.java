package com.demo.asset.service.repositories;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.demo.asset.service.models.Asset;

@Repository
public class AssetRepository {
	private Map<UUID, Asset> asset;

	public AssetRepository() {
		asset = new HashMap<>();
	}

	public void cleanup() {
		asset.clear();
	}
	
	public List<Asset> getAll() {
		return new ArrayList<Asset>(asset.values());
	}
	
	public Asset get(String id) {
		try {
			UUID uuid = UUID.fromString(id);
			return asset.get(uuid);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}
	
	public Asset store(Asset updateAsset) {
		UUID uuid = UUID.fromString(updateAsset.getId());
		asset.put(uuid, updateAsset);
		
		return updateAsset;
	}

	public Asset delete(String id) {
		Asset deleteAsset = get(id);
		if (deleteAsset == null) return null;
		
		UUID uuid = UUID.fromString(deleteAsset.getId());
		asset.remove(uuid);
		return deleteAsset;
	}
}
