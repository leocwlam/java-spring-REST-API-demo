package com.demo.asset.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.asset.service.models.Asset;
import com.demo.asset.service.services.AssetService;

@RestController
@RequestMapping("v1/assets")
class AssetController {
	
	@Autowired
	private AssetService service;
	
	@GetMapping()
	public ResponseEntity<List<Asset>> getAssets() {
		List<Asset> assets = service.getAssets();

		return ResponseEntity
				.status(200)
				.body(assets);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Asset> getAssetById(@PathVariable("id") String id) {
		Asset asset = service.getAsset(id);
		return ResponseEntity
				.status(200)
				.body(asset);
	}

	@PostMapping()
	public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
		Asset newAsset = service.createAsset(asset.getName(), asset.getType(), asset.getDescription());
		return ResponseEntity
				.status(201)
				.body(newAsset);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Asset> updateAsset(@PathVariable("id") String id, @RequestBody Asset asset) {
		Asset updatedAsset = service.updateAsset(id, asset.getName(), asset.getType(), asset.getDescription());	
		return ResponseEntity
				.status(200)
				.body(updatedAsset);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Asset> deleteAssetById(@PathVariable("id") String id) {
		Asset deletedAsset = service.deleteAsset(id);
		return ResponseEntity
				.status(200)
				.body(deletedAsset);
	}
}
	