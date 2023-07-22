package com.demo.asset.service.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.demo.asset.service.models.Asset;
import com.demo.asset.service.services.AssetService;

@WebMvcTest(controllers = AssetController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AssetControllerTests {
	@Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
	@MockBean
	private AssetService assetService;
    
    @Test
    public void Get_Assets_Return_Assets() throws Exception {
		Asset asset1 = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc 1", LocalDateTime.now());
		Asset asset2 = new Asset(UUID.randomUUID().toString(), "test2", 2, "test desc 2", LocalDateTime.now());
		List<Asset> assets = new ArrayList<Asset>() {{
		    add(asset1);
		    add(asset2);
		}};
		
    	when(assetService.getAssets()).thenReturn(assets);

    	ResultActions response = mockMvc.perform(get("/v1/assets")
			.contentType(MediaType.APPLICATION_JSON));
    	
    	response.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(assets.size())));
    }
	
    @Test
    public void Get_Asset_Return_Asset() throws Exception {
    	String id = UUID.randomUUID().toString();
		Asset asset = new Asset(id, "test1", 1, "test desc 1", LocalDateTime.now());
		
    	when(assetService.getAsset(Mockito.any(String.class))).thenReturn(asset);

    	ResultActions response = mockMvc.perform(get("/v1/assets/" + id)
			.contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(asset)));
    	
    	response.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(asset.getId())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(asset.getName())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(asset.getType())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(asset.getDescription())));
    }

    @Test
    public void Get_Non_Exist_Asset_Return_Null() throws Exception {
    	String id = UUID.randomUUID().toString();
    	when(assetService.getAsset(Mockito.any(String.class))).thenReturn(null);

    	ResultActions response = mockMvc.perform(get("/v1/assets/"+id)
			.contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(null)));
    	response.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }
    
    @Test
    public void Create_Asset_Return_Created_Asset() throws Exception {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc 1", LocalDateTime.now());
        given(assetService.createAsset(asset.getName(), asset.getType(), asset.getDescription()))
        	.willAnswer((invocation -> asset));
        
        ResultActions response = mockMvc.perform(post("/v1/assets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(asset)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(asset.getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(asset.getName())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(asset.getType())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(asset.getDescription())));
    }
    
    @Test
    public void Update_Asset_Return_Updated_Asset() throws Exception {
    	String id = UUID.randomUUID().toString();
		Asset asset = new Asset("", "test1", 1, "test desc 1", LocalDateTime.now());

        given(assetService.updateAsset(id, asset.getName(), asset.getType(), asset.getDescription()))
        	.willAnswer((invocation -> new Asset(invocation.getArgument(0),
        										 invocation.getArgument(1),
        										 invocation.getArgument(2),
        										 invocation.getArgument(3),
        										 asset.getCreated())));
        
        asset.setId(id);
        ResultActions response = mockMvc.perform(put("/v1/assets/" + asset.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(asset)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(asset.getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(asset.getName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(asset.getType())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(asset.getDescription())));
    }
    
    @Test
    public void Update_Non_Exist_Asset() throws Exception {
    	String id = UUID.randomUUID().toString();
		Asset asset = new Asset("", "test1", 1, "test desc 1", LocalDateTime.now());

        given(assetService.updateAsset(id, asset.getName(), asset.getType(), asset.getDescription()))
        	.willAnswer((invocation -> null));
        
        asset.setId(id);
        ResultActions response = mockMvc.perform(put("/v1/assets/" + asset.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(asset)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    public void Delete_Asset_Return_Deleted_Asset() throws Exception {
		Asset asset = new Asset(UUID.randomUUID().toString(), "test1", 1, "test desc 1", LocalDateTime.now());

        given(assetService.deleteAsset(asset.getId()))
        	.willAnswer((invocation -> asset));
        
        ResultActions response = mockMvc.perform(delete("/v1/assets/" + asset.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(asset)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(asset.getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(asset.getName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(asset.getType())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(asset.getDescription())));
    }

    @Test
    public void Delete_Non_Exist_Asset() throws Exception {
    	String id = UUID.randomUUID().toString();
		given(assetService.deleteAsset(id))
        	.willAnswer((invocation -> null));
        
        ResultActions response = mockMvc.perform(delete("/v1/assets/"+id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(null)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }
}
