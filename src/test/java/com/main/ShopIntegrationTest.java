package com.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.model.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShopIntegrationTest {

    @Autowired
    private Map<String,Shop> shopsDemo;
    @Autowired
    private MockMvc mockMvc;
    private List<Shop> setupShops;
    private Shop newShop;


    @Before
    public void setUp() throws Exception {
        setupShops = Arrays.asList(
                new Shop("shop_N1", 1, "WC2E 8HD", "GB", null, null, null, null),
                new Shop("shop_N2", 2, "DN36 5YG", "GB", null, null, null, null),
                new Shop("shop_N3", 3, "KT20 6SD", "GB", null, null, null, null));


        newShop = new  Shop("shop_N4", 4, "WC1E 8HD", "GB", null, null, null, null); 

        for (Shop shop : setupShops) {         	
         shopsDemo.put(shop.getShopName(), shop);
        }
    }

    @After
    public void tearDown() throws Exception {
    	shopsDemo.clear();
    }

    @Test
    public void getShop() throws Exception {
        this.mockMvc
                .perform(get("/shops/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.shopEntityList.length()").value(setupShops.size()));
    }

    @Test
    public void saveInvalidShop() throws Exception {
        this.mockMvc
                .perform(post("/shops/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isString());
    }
/*
    @Test
    public void saveNonExistantShop() throws Exception {
        this.mockMvc
                .perform(post("/shops/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getShopAsJSON(newShop)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/shops/shop_N4"))
                .andExpect(content().string(""));
    }*/

/*    @Test
    public void saveExistingShop() throws Exception {
        Shop shopUpdate = new Shop("shop_NX", 1, "SY23 4BH", "GB", null, null, null, null); 
        String previousPostCode = setupShops.get(0).getShopAddress().getZipCode();

        this.mockMvc
                .perform(post("/shops/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getShopAsJSON(shopUpdate)))
                .andExpect(status().isOk())
                .andExpect(header().string("location", "http://localhost:8080/shops/shop1"))
                .andExpect(jsonPath("$.version").value(0))
                .andExpect(jsonPath("$.shopAddress.zipCode").value(previousPostCode));
    }*/

    @Test
    public void getNonExistantShop() throws Exception {
        this.mockMvc
                .perform(get("/shops/devnull"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getExistingShop() throws Exception {
        this.mockMvc
                .perform(get("/shops/" + setupShops.get(0).getShopName()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"shop_N1\",\"shopAddress\":{\"number\":\"1\",\"zipCode\":\"WC2E 8HD\",\"countryID\":\"GB\",\"geoloc\":{\"latitude\":null,\"longitude\":null,\"distance\":null}},\"_links\":{\"self\":{\"href\":\"http://localhost:8080/shops/shop1\"}}}"));
    }
/*
    private String getShopAsJSON(Shop shop) {
        return "{\n" +
                "\t\"name\": \"" + shop.getShopName() + "\",\n" +
                "\t\"shopAddress\" : {\n" +
                "\t\t\"number\": \"" + shop.getShopAddress().getNumber() + "\",\n" +
                "\t\t\"postCode\": \"" + shop.getShopAddress().getZipCode() + "\"\n" +
                "\t}\n" +
                "}";
    }*/

}