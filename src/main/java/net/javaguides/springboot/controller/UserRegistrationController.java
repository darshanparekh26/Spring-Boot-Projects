package net.javaguides.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import net.javaguides.springboot.dto.UserRegistrationDto;
import net.javaguides.springboot.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto registrationDto, BindingResult result, Model model) {
    	
    	if (result.hasErrors()) {
            return "registration";
        }
    	
        try {
            userService.save(registrationDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registration"; // Return registration form with error message
        }
        return "redirect:/registration?success";
    }
}