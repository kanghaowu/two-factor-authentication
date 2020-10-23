package idv.google.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import idv.google.auth.model.User;
import idv.google.auth.service.AuthService;

@Controller
public class AuthController {

	@Autowired
	private AuthService service;

	@GetMapping("/signup")
	public String signupForm(Model model) {
		model.addAttribute("user", new User());
		return "index";
	}

	@PostMapping("/signup")
	public String signupSubmit(@ModelAttribute User user, Model model) {
		model.addAttribute("account", user.getAccount());
		model.addAttribute("qrcodeURI", service.getQrcode(user.getAccount()));
		return "verify";
	}

	@RequestMapping("/verify")
	public String verify(@RequestParam(required=true) String account, @RequestParam(required=true) int code, Model model) {
    	boolean result = service.validCode(account, code);
    	model.addAttribute("result", result);
        return "result";
    }

}