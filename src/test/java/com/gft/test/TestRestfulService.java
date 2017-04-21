package com.gft.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.gft.restservice.model.Shop;
import com.gft.restservice.services.ShopService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestRestfulService {
	
	@Autowired
	private MockMvc mockMvc;
	private List<Shop> temporaryShops;
	@Autowired
	private ShopService shopsDemoTest;
	
	@Before
    public void setUp() throws Exception {
		temporaryShops = Arrays.asList(
                new Shop("shop_N1",1,"46185","ES",null,null,null,null),                
                new Shop("shop_N2",2,"46001","ES",null,null,null,null),
                new Shop("shop_N3",3,"11100","ES",null,null,null,null)
        );
		
		for (Shop shop : temporaryShops) {
			shopsDemoTest.getGeoService().geolocate(shop);
			shopsDemoTest.addShop(shop);
        }
	}
		
	@Test
    public void saveNewShop() throws Exception {        
		String jsonShop = "{\"shopName\":\"shop_N4\",\"shopAddress\":{\"number\":4,\"zipCode\":\"46001\",\"countryID\":\"ES\"}}";
		this.mockMvc.perform(post("/shops/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonShop)).andExpect(status().isCreated());
	}
	
	@Test
    public void saveExistingShop() throws Exception {        
		String jsonShop = "{\"shopName\":\"shop_N3\",\"shopAddress\":{\"number\":5,\"zipCode\":\"11100\",\"countryID\":\"ES\"}}";
		this.mockMvc.perform(post("/shops/").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonShop)).andExpect(status().isOk());
	}
	
	@Test
    public void getShops() throws Exception {
        this.mockMvc.perform(get("/shops/")).andExpect(status().isOk());
    }

}
