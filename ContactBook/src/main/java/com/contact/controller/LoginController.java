package com.contact.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.contact.exceptions.EmailAlreadyExistException;
import com.contact.exceptions.PhoneNumberAlreadyExistException;
import com.contact.model.User;
import com.contact.services.UserServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	private UserServices userServices;
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/log")
	public String getLogin(){
		logger.info("User redirected to Login Page");
		return "login";
	}
	@GetMapping("/register")
	public String helloPage() {
		logger.info("User redirected to Register Page");
		return "register";
	}
	@GetMapping("/")
	public String viewHomePage() {
		logger.info("User visited our website");
		return "home";
	}
	
	@PostMapping("/ProcessRegister")
	public ModelAndView addUser(@ModelAttribute("user") User user)throws PhoneNumberAlreadyExistException,EmailAlreadyExistException{
		ModelAndView mav = new ModelAndView();
		try {
		userServices.saveUser(user);
		mav.addObject("user",user);
		mav.addObject("successMessage","Your successfully Registered");
		mav.setViewName("login");
		logger.info(user.getUName() +" user has Successful registered");
		}
		catch(PhoneNumberAlreadyExistException pae) {
			mav.setViewName("ExceptionError");
			mav.addObject("errorMessage",pae.getMessage());
			logger.error(user.getUName() +" has an error in "+ pae.getMessage());
		}
		catch(EmailAlreadyExistException eae) {
			mav.setViewName("ExceptionError");
			mav.addObject("errorMessage", eae.getMessage());
			logger.error(user.getUName() +" has an error in "+ eae.getMessage());

		}
		catch (Exception e) {
			mav.setViewName("ExceptionError");
			mav.addObject("errorMessage",e.getMessage());
			logger.error(user.getPhoneNumber() +" has an error in "+ e.getMessage());

		}
return mav;
	}
	
	
	@GetMapping("/homepage")
	public String viewHome(Model model){
		 int u = userServices.uidByLid();
		 User user = userServices.getUser(u);
		model.addAttribute("user",user);
		logger.info(user.getUName()+" redirected to Smart Contact home page");
		return "fnavbar";
	}

	@PostMapping("/check")
	public String validate(@RequestParam("phoneNumber")Long PhoneNumber ,@RequestParam("passwd") String passwd , Model model,HttpSession session) {
		if(userServices.validateCredentials(PhoneNumber,passwd)){
			User u = userServices.findByPhoneNumberAndPasswd(PhoneNumber, passwd);
			//session.setAttribute("usersDetails", u);
			userServices.loggingDelete();
			userServices.insertLogging(PhoneNumber, passwd,u.getUid());
			model.addAttribute("user",u);
			logger.info(u.getUName()+" has successfully Logged in");
		return "fnavbar";
		}
 		model.addAttribute("invalidCredentials","UserName and Password doesn't match");
		logger.error("User's credentials doesn't match");
		return "InvalidCredentials";
		}
	
	 @GetMapping("/delete")
	 public String deleteLogg() {
		 int u = userServices.uidByLid();
		 User user = userServices.getUser(u);
		 this.userServices.loggingDelete();
		 logger.info(user.getUName()+" is Logged Out");
		 return "Logout";
	 }
	 
	 
	 @GetMapping("/getProfile")
		public String showFormForUpdate( Model model) {
		 if(userServices.getLoggingDetails()!=null) {
		 int u = userServices.uidByLid();
			// get User from the service
		 User user = userServices.getUser(u);
			
			// set user as a model attribute to pre-populate the form
			model.addAttribute("user", user);
			logger.info(user.getUName()+" viewed Contact profile");
			return "profile";
		 }
			else {
				logger.error("No Active user found");
				 return "redirect:/";
			 }
		}
	 @GetMapping("/getProfile/{cuid}")
		public String showFormForUpdate(@PathVariable ( value = "cuid") int cuid, Model model) {
		 if(userServices.getLoggingDetails()!=null) {	
		 // get user from the service
		 User user = userServices.getUser(cuid);
			// set  as a model attribute to pre-populate the form
			model.addAttribute("contact", user);
			logger.info(user.getUName()+" viewed his profile.");
			return "ViewProfile";
		 }
			else {
				logger.error("No Active user found");
				 return "redirect:/";
			 }
		}
	 @GetMapping("/contactUs")
	 public String contactUs() {
		 logger.info("user Clicked on contact us");
		 return "UserContactus";
	 }
	 
	 @GetMapping("/forgot")
		public String getForgot() {
			 logger.info("User has clicked Forgot Password");
			return "ForgotPasswd";
		 
	 }
	 
	 @GetMapping("/forgotProcess")
	 public String getPasswd(@RequestParam("phoneNumber")long PhoneNumber ,@RequestParam("dob") String dob , Model model) {
		 User user = userServices.forgottenPassword(PhoneNumber, dob);
		if (user!=null) {
		 model.addAttribute("user",user);
		 logger.info(user.getUName()+"has succesfully received his Password");
		 model.addAttribute("passwordMessage",user.getPasswd());
		 return "login";
		}
 		model.addAttribute("invalidCredentials","UserName and DOB doesn't match");
		logger.error("User's credentials doesn't match");
		return "InvalidCredentials";
	 }
	 
	 
	 @GetMapping("/reset")
	 public String resetPass() {
			if(userServices.getLoggingDetails()!=null) {
		 int u = userServices.uidByLid();
			// get User from the service
		 User user = userServices.getUser(u);
		 logger.info(user.getUName() +" wants to reset his password");
	 	return "ResetPassword";
			}
			else {
				logger.error("No Active User Found");
				 return "redirect:/";
			 }
	 }
	 
	 @PostMapping("/resetPasswd")
	 public String resetPasswdProcess(@RequestParam("oldpw") String opw,@RequestParam("newpw") String npw,Model model) throws PhoneNumberAlreadyExistException, EmailAlreadyExistException {
			if(userServices.getLoggingDetails()!=null) {
		 int u = userServices.uidByLid();
		User user= userServices.getUser(u);
	 	if(user.getPasswd().equals(opw)) {
	 		user.setPasswd(npw);
	 		userServices.updateUser(user);
	 		model.addAttribute("successMessage","Password Updated Succesfully");
			model.addAttribute("user", user);
			logger.info(user.getUName()+" has succesfully changed his password");
	 		return "profile";
	 	}
 		model.addAttribute("errorMessage","Password doesn't match");
 		logger.error(user.getUName()+" has entered wrong old passsword ");
	 	return "InvalidCredentials";
	 }
	 	else {
			logger.error("No Active User Found");
			 return "redirect:/";
		 }
	 }
	 
	 @GetMapping("/thankyou")
	 public String ThankYou() {
			if(userServices.getLoggingDetails()!=null) {
		 return "ThankYou";
	 }
			else {
				logger.error("No Active User Found");
				 return "redirect:/";
			 }
			
	 
}
	 }
