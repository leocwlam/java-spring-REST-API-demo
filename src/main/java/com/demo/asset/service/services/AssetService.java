package com.demo.asset.service.services;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.asset.service.models.Asset;
import com.demo.asset.service.repositories.AssetRepository;

@Service
public class AssetService {
	@Autowired
	private AssetRepository repository;
	
	public List<Asset> getAssets() {
		return repository.getAll();
	}

	public Asset getAsset(String id) {
		return repository.get(id);
	}
	
	public Asset createAsset(String name, int type, String description) {
		Asset asset = new Asset("", name, type, description, LocalDateTime.now());
		asset.setId(UUID.randomUUID().toString());
		return repository.store(asset);
	}
	
	public Asset updateAsset(String id, String name, int type, String description) {
		Asset asset = getAsset(id);
		if (asset == null) return null;
		
		asset.setName(name);
		asset.setType(type);
		asset.setDescription(description);
		return repository.store(asset);
	}
	
	public Asset deleteAsset(String id) {
		Asset asset = repository.delete(id);
		return asset;
	}
}
