package br.com.starstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

	// HOME
	
		@GetMapping("/")
		public String home() throws Exception {
			return "- STARSTORE REST API -"
					.concat("\nGET: /starstore/products")
					.concat("\nPOST: /starstore/product")
					.concat("\nGET: /starstore/history")
					.concat("\nGET: /starstore/history/{clientId}")
					.concat("\nPOST: /starstore/buy");
		}
		
		// FIM HOME	
}
