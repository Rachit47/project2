package com.springbootproject.librarium365.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "Librarium365 API";
	}

	@GetMapping("/login")
	public String login() {
		return "Login required";
	}

	@GetMapping("/admin")
	public String admin() {
		return "Librarium Admin panel";
	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "Forbidden action";
	}
}
