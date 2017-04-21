package com.gft.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.restservice.model.Shop;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootApplication(scanBasePackages = "com.gft.restservice")

public class TestSerializedShop {

	private JacksonTester<Shop> jsonShop;

	@Before
	public void setUp() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
	}

	@Test
	public void serializeJsonShop() throws IOException {
		Shop shopTest1 = new Shop("shop_N1", 1, "WC2E 8HD", "GB", null, null, null, null);

		assertThat(this.jsonShop.write(shopTest1)).isEqualToJson("shopTest1.json");
		assertThat(this.jsonShop.write(shopTest1)).hasJsonPathStringValue("@.shopName");
		assertThat(this.jsonShop.write(shopTest1)).extractingJsonPathStringValue("@.shopName").isEqualTo("shop_N1");
	}

	@Test
	public void testDeserialize() throws Exception {
		String jsonShop = "{\"shopName\":\"shop_N1\",\"shopAddress\":{\"number\":1,\"zipCode\":\"WC2E 8HD\",\"countryID\":\"GB\",\"geoloc\":{\"latitude\":null,\"longitude\":null}}}";
		assertThat(this.jsonShop.parse(jsonShop))
				.isEqualTo(new Shop("shop_N1", 1, "WC2E 8HD", "GB", null, null, null, null));
		assertThat(this.jsonShop.parseObject(jsonShop).getShopName().equals("shop_N1"));
	}
}