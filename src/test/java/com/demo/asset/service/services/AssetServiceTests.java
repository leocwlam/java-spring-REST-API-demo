package com.demo.asset.service.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import com.demo.asset.service.models.Asset;
import com.demo.asset.service.repositories.AssetRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AssetServiceTests {

    @Mock
    private AssetRepository repository;

    @InjectMocks
	private AssetService assetService;
	
	@Test
	public void Get_Asset_With_Exist_Asset() {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc", LocalDateTime.now());
		
        when(repository.get(Mockito.any(String.class))).thenReturn(asset);
		
        Asset getAsset = assetService.getAsset(asset.getId());

        Assertions.assertThat(getAsset).isNotNull();
        Assertions.assertThat(getAsset).isEqualTo(asset);
	}
	
	@Test
	public void Get_Asset_With_Non_Exist_Asset() {
        when(repository.get(Mockito.any(String.class))).thenReturn(null);
		
        Asset getAsset = assetService.getAsset(UUID.randomUUID().toString());

        Assertions.assertThat(getAsset).isNull();
	}

	@Test
	public void Get_Assets() {
		Asset asset1 = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc 1", LocalDateTime.now());
		Asset asset2 = new Asset(UUID.randomUUID().toString(), "test2", 2, "test desc 2", LocalDateTime.now());
		List<Asset> list = new ArrayList<Asset>() {{
		    add(asset1);
		    add(asset2);
		}};
		
        when(repository.getAll()).thenReturn(list);
		
        List<Asset> assets = assetService.getAssets();

        Assertions.assertThat(assets.size()).isEqualTo(2);
        Assertions.assertThat(assets.stream().filter(e -> e.getId().equals(asset1.getId())).findFirst().orElseGet(null)).isEqualTo(asset1);
		Assertions.assertThat(assets.stream().filter(e -> e.getId().equals(asset2.getId())).findFirst().orElseGet(null)).isEqualTo(asset2);
	}
	
	@Test
	public void Delete_Asset_With_Exist_Asset() {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc", LocalDateTime.now());
		
        when(repository.delete(Mockito.any(String.class))).thenReturn(asset);
		
        Asset deletedAsset = assetService.deleteAsset(asset.getId());

        Assertions.assertThat(deletedAsset).isNotNull();
        Assertions.assertThat(deletedAsset).isEqualTo(asset);
	}

	@Test
	public void Delete_Asset_With_Non_Exist_Asset() {
        when(repository.delete(Mockito.any(String.class))).thenReturn(null);
		
        Asset deletedAsset = assetService.deleteAsset(UUID.randomUUID().toString());

        Assertions.assertThat(deletedAsset).isNull();
	}
	
	@Test
	public void Save_Asset_Return_New_Asset() {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc", LocalDateTime.now());

		when(repository.store(Mockito.any(Asset.class))).thenReturn(asset);
		
        Asset newAsset = assetService.createAsset(asset.getName(), asset.getType(), asset.getDescription());

        Assertions.assertThat(newAsset).isNotNull();
        Assertions.assertThat(newAsset).isEqualTo(asset);
	}

	@Test
	public void Update_Asset_Return_Changed_Asset() {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc", LocalDateTime.now());

		when(repository.get(Mockito.any(String.class))).thenReturn(asset);
		when(repository.store(Mockito.any(Asset.class))).thenReturn(asset);
		
        Asset changeAsset = assetService.updateAsset(asset.getId(), asset.getName(), asset.getType(), asset.getDescription());

        Assertions.assertThat(changeAsset).isNotNull();
        Assertions.assertThat(changeAsset).isEqualTo(asset);
	}

	@Test
	public void Update_Non_Exist_Id() {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc", LocalDateTime.now());

		when(repository.get(Mockito.any(String.class))).thenReturn(null);
		
        Asset changeAsset = assetService.updateAsset(asset.getId(), asset.getName(), asset.getType(), asset.getDescription());

        Assertions.assertThat(changeAsset).isNull();
	}
}
