package edu.hm.wedoit.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	public @ResponseBody String printWelcome() {
		//model.addAttribute("message", "Hello world!");
		System.out.println("test");
		return "root";
	}

	@RequestMapping("/sers")
	public @ResponseBody String printServus() {
		//model.addAttribute("message", "Hello world!");
		System.out.println("servus");
		return "servus";
	}
}