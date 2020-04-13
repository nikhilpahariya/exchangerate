package com.hsbc.exchangerate.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hsbc.exchangerate.model.ExchangeRatesModel;

@RestController
@RequestMapping(path = "/api/exchangeratesapi")
public class ExchangeRateController {
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping({ "/", "/{date}" })
	public ExchangeRatesModel getExchangeRates(@RequestParam(value = "base", required = false, defaultValue = "EUR") String base,
			@RequestParam(value = "symbols", required = false, defaultValue = "GBP,USD,HKD") String symbols,
			@PathVariable Optional<String> date) {
		
		StringBuilder uriBuilder = new StringBuilder();
		uriBuilder
			.append("https://api.ratesapi.io/api/")
			.append(date.orElse("latest"))
			.append("?")
			.append("base=" + base)
			.append("&")
			.append("symbols=" + symbols);
			
		return restTemplate.getForObject(uriBuilder.toString(), ExchangeRatesModel.class);
	}
}
