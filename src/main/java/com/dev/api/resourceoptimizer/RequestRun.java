package com.dev.api.resourceoptimizer;

import java.util.concurrent.Callable;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public class RequestRun implements Callable<String>  {
	
	private final RestTemplate restTemplate;
	private final String key;

    public RequestRun(RestTemplate restTemplate, String key) {
    
        this.restTemplate = restTemplate;
        this.key = key;
    }

    public String getResponsePlainJSON() {
    	System.out.println("Executing get Call");
        String url = "https://httpbin.org/"+this.key;
        String result = null;
        try {
        	result = this.restTemplate.getForObject(url, String.class);
        }catch(HttpClientErrorException ex) {
        	ex.printStackTrace();
        	result = ex.getMessage();
        }
        return result;
    }



	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		Thread.sleep(10000);
		return getResponsePlainJSON();
	}
	
	

}
