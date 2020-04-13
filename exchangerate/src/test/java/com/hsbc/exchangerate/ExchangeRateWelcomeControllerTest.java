package com.hsbc.exchangerate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeRateWelcomeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void exchangeratesTest() throws Exception {
		this.mockMvc
			.perform(get("/exchangerates").header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:admin".getBytes())))
			.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void historicalExchangeRatesTest() throws Exception {
		this.mockMvc
			.perform(get("/historicalexchangerates").header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:admin".getBytes())))
			.andDo(print()).andExpect(status().isOk());
	}
}
