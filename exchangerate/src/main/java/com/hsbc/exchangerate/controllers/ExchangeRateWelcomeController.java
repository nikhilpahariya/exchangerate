package com.hsbc.exchangerate.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.hsbc.exchangerate.model.ExchangeRatesModel;

@Controller
public class ExchangeRateWelcomeController {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping({ "/exchangerates", "/exchangerates/{date}" })
	public String exchangerates(Model model, 
			@RequestParam(value = "base", required = false, defaultValue = "EUR") String base,
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
		
		ExchangeRatesModel exchangeRate =  restTemplate.getForObject(uriBuilder.toString(), ExchangeRatesModel.class);
		
		model.addAttribute("exchangeRates", exchangeRate);
		return "exchangerates";
	}
	
	@GetMapping({ "/historicalexchangerates", "/historicalexchangerates/{date}" })
	public String historicalExchangeRates(Model model, 
			@RequestParam(value = "base", required = false, defaultValue = "EUR") String base,
			@RequestParam(value = "symbols", required = false, defaultValue = "GBP,USD,HKD") String symbols,
			@PathVariable Optional<String> date) {
		
		String presentDate = null;
		if (date.isEmpty()) {
			ExchangeRatesModel exchangeRate = restTemplate.getForObject("https://api.ratesapi.io/api/latest", ExchangeRatesModel.class);
			presentDate = exchangeRate.getDate();
		} else {
			presentDate = date.get();
		}
		
		LocalDate inputDate = LocalDate.parse(presentDate, formatter);
		List<ExchangeRatesModel> historicalExchangeRates = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			StringBuilder uriBuilder = new StringBuilder();
			uriBuilder
				.append("https://api.ratesapi.io/api/")
				.append(inputDate.toString())
				.append("?")
				.append("base=" + base)
				.append("&")
				.append("symbols=" + symbols);
			
			ExchangeRatesModel exchangeRate =  restTemplate.getForObject(uriBuilder.toString(), ExchangeRatesModel.class);
			exchangeRate.setDate(inputDate.getMonth().name() + " " + inputDate.getYear());
			historicalExchangeRates.add(exchangeRate);
			
			inputDate = inputDate.minusMonths(1);
		}
		
		model.addAttribute("historicalExchangeRates", historicalExchangeRates);
		model.addAttribute("symbols", symbols);
		return "historicalexchangerates";
	}
}