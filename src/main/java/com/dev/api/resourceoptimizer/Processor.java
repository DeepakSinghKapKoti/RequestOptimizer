package com.dev.api.resourceoptimizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Processor {

	private final RestTemplate restTemplate;

	private static List<String> keyList = new ArrayList<String>();
	private static List<String> waitings = new ArrayList<String>();
	private static ExecutorService executor = Executors.newFixedThreadPool(1);
	private static List<Future<String>> runList = new ArrayList<Future<String>>();

	public Processor(RestTemplateBuilder restTemplateBuilder) {

		this.restTemplate = restTemplateBuilder.build();
	}

	public String process(String key) {
		String value = null;
		if (keyList.contains(key)) {
			waitings.add(key);
			int i = keyList.indexOf(key);
			System.out.println(key + " is waiting");
			while (!runList.get(i).isDone()) {

			}
			System.out.println(key + " is completed");
			waitings.remove(key);
			
				try {
					value = runList.get(i).get();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					value = e.getMessage();
				}
				if (!waitings.contains(key)) {
					keyList.remove(key);
					runList.remove(runList.get(i));
				}

			

		} else {
			keyList.add(key);

			
				RequestRun run = new RequestRun(this.restTemplate, key);

				Future<String> future = executor.submit(run);
				runList.add(future);
				while (!future.isDone()) {

				}
				if (future.isCancelled()) {
					value = "Cancelled";
					if (!waitings.contains(key)) {
						keyList.remove(key);
						runList.remove(future);
					}

				} else if (future.isDone()) {
					try {
						value = future.get();
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						value = e.getMessage();
					}
					if (!waitings.contains(key)) {
						keyList.remove(key);
						runList.remove(future);
					}

				}

		
		}

		
		return value;
	}

}
