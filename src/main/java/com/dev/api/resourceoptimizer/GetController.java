package com.dev.api.resourceoptimizer;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * 
 * @author Deepak Singh Kapkoti
 *
 */

@RestController
@RequestMapping("/api")
public class GetController {
	
	@Autowired
	Processor processor;
	
	@GetMapping("/{path}")
	public String getFunction(@PathVariable String path) {
		
		System.out.println(path);
		return processor.process(path);
		
		
	}
	
	@GetMapping("/")
	public String welcome() {
		
		
		return "Hi & Wellcome";
		
		
	}
	
	

}
