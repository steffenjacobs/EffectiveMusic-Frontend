package me.steffenjacobs.effectivemusic.frontend.server.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class BackendRestController {

	@PostMapping(value = "/rest/get", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> restGetQuery(String uri) {
		String result = new RestTemplate().getForObject(uri, String.class);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(value = "/rest/post", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> restPostQuery(String uri) {
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded");
		HttpEntity<String> request = new HttpEntity<>(headers);
		String result = new RestTemplate().postForObject(uri, request, String.class);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
