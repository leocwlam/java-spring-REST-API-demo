package com.demo.asset.service.respositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.asset.service.models.Asset;
import com.demo.asset.service.repositories.AssetRepository;

@SpringBootTest
public class AssetRepositoryTests {

	@Autowired
	private AssetRepository repository;
	
	@Test
	public void Save_Return_New_Asset() {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc", LocalDateTime.now());
		repository.store(asset);
		Asset newAsset = repository.get(asset.getId());
		
		Assertions.assertThat(newAsset).isNotNull();
		Assertions.assertThat(newAsset).isEqualTo(asset);
	}
	
	@Test
	public void Change_Return_Updated_Asset() {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc", LocalDateTime.now());
		repository.store(asset);
		
		Asset changeAsset = repository.get(asset.getId());
		changeAsset.setName("change test 1");
		changeAsset.setType(11);
		changeAsset.setDescription("change desc done");;
		repository.store(changeAsset);
		Asset checkAsset = repository.get(asset.getId());
		
		Assertions.assertThat(checkAsset).isNotNull();
		Assertions.assertThat(checkAsset).isEqualTo(changeAsset);
	}

	@Test
	public void Get_Asset_Id() {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc", LocalDateTime.now());
		repository.store(asset);
		Asset getAsset= repository.get(asset.getId());
		
		Assertions.assertThat(getAsset).isEqualTo(asset);
	}
	
	@Test
	public void Get_Invalid_Asset_Id() {
		Asset asset= repository.get("1");
		Assertions.assertThat(asset).isNull();
	}
	
	@Test
	public void Deleted_Asset() {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc", LocalDateTime.now());
		repository.store(asset);
		Asset deletedAsset = repository.delete(asset.getId());
		Asset checkAsset = repository.get(asset.getId());
		
		Assertions.assertThat(checkAsset).isNull();
		Assertions.assertThat(deletedAsset).isEqualTo(asset);
	}
	
	@Test
	public void Deleted_Non_Exist_Asset() {
		Asset deletedAsset = repository.delete(UUID.randomUUID().toString());
		
		Assertions.assertThat(deletedAsset).isNull();
	}

	@Test
	public void Get_All_Assets() {
		repository.cleanup();
		Asset asset1 = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc 1", LocalDateTime.now());
		repository.store(asset1);
		Asset asset2 = new Asset(UUID.randomUUID().toString(), "test2", 2, "test desc 2", LocalDateTime.now());
		repository.store(asset2);
		
		List<Asset> assets = repository.getAll();
		
		Assertions.assertThat(assets.size()).isEqualTo(2);
		Assertions.assertThat(assets.stream().filter(e -> e.getId().equals(asset1.getId())).findFirst().orElseGet(null)).isEqualTo(asset1);
		Assertions.assertThat(assets.stream().filter(e -> e.getId().equals(asset2.getId())).findFirst().orElseGet(null)).isEqualTo(asset2);
	}
}
