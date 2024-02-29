package com.contact.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.contact.model.Contact;
import com.contact.model.User;
import com.contact.services.ContactService;
import com.contact.services.UserServices;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ContactController {
	@Autowired
	private ContactService contactService;
	@Autowired
	private UserServices userServices;
	
	Logger logger=LoggerFactory.getLogger(ContactController.class);
	
	@GetMapping("/add")
	public String contactPage(Model model) {
		
		if(userServices.getLoggingDetails()!=null) {
			int u = userServices.uidByLid();
			User user=userServices.getUser(u);
			model.addAttribute("user",user);
		logger.info(user.getUName() +" Clicked on Add Contact");
		return "AddContact";
		}
		else {
			logger.error("No Active User Found");
			 return "redirect:/";
		 }
	}
	@GetMapping("/showContacts")
	public String show(Model model) {
		if(userServices.getLoggingDetails()!=null) {
		//session.getAttribute()
		int u = userServices.uidByLid();
		User user=userServices.getUser(u);
		model.addAttribute("user",user);
		List<Contact> contact = contactService.contactByLid(u);
		//List<Contact> contacts = contactService.getAllContacts();
		model.addAttribute("contacts", contact);
		model.addAttribute("user",user);
		logger.info(user.getUName() +" Viewed all his contacts");
		return "index";
		}
		else {
			logger.error("No Active User Found");
			 return "redirect:/";
		 }
	}
	
	@PostMapping("/addCont")
	public ModelAndView addCOntact(@ModelAttribute("contact") Contact contact,Model model){
		if(userServices.getLoggingDetails()!=null) {
		int u = userServices.uidByLid();
		User user= userServices.getUser(u);
		contact.setUser(user);
		user.getContacts().add(contact);
		model.addAttribute("user",user);
//		userServices.insertuserIdByLid(u);
//		contact.setUser()
//		contact.setUser((User)session.getAttribute("usersDetails"));
		this.contactService.saveContact(contact);
		logger.info(user.getUName()+" has saved "+contact.getCName());
		return new ModelAndView("redirect:/showContacts");
		}
		else {
			logger.error("No Active User Found");
			 return new ModelAndView("redirect:/");
		 }
	}
	@GetMapping("/showFormForUpdate/{cId}")
	public String showFormForUpdate(@PathVariable ( value = "cId") int cid, Model model) {
		if(userServices.getLoggingDetails()!=null) {
			int u = userServices.uidByLid();
			User user=userServices.getUser(u);
			model.addAttribute("user",user);
		// get Contacts from the service
		Contact contact = contactService.getContactByCid(cid);
		// set Contacts as a model attribute to pre-populate the form
		model.addAttribute("contact", contact);
		logger.info(contact.getCName()+" is redirected to update contact");
		return "UpdateContact";
		}
		else {
			logger.error("No Active User Found");
			 return "redirect:/";
		 }
	}
	
	@GetMapping("/deleteContact/{cid}")
	public String deleteContact(@PathVariable (value = "cid") int cid) {
		if(userServices.getLoggingDetails()!=null) {
			Contact contact = contactService.getContact(cid);
		// call delete Contact method
		this.contactService.contactDelete(cid);
		logger.info(contact.getCName()+" is deleted");
		return "redirect:/showContacts";
		}
		else {
			logger.error("No Active User Found");
			 return "redirect:/";
		 }
	}
	
	@GetMapping("/searchContacts")
	public String searchContact(@RequestParam("prefix") String prefix, Model model) {
		if(userServices.getLoggingDetails()!=null) {
		int u = userServices.uidByLid();
		User user = userServices.getUser(u);
		List<Contact> contacts = contactService.searchPrefix(prefix,u);
		if(!(contacts.isEmpty())) {
		model.addAttribute("user",user);
		model.addAttribute("contacts", contacts);
		logger.info(user.getUName()+" searched for "+prefix +"in his contact list");
		return "index";
		}
		else {
			model.addAttribute("user",user);
			model.addAttribute("noResult","No result Found");
			logger.info("No results found for given name");
			return "index";
		}
		}
		else {
			logger.error("No Active User Found");
			 return "redirect:/";
		 }
	}
	@GetMapping("/view/{cId}")
	public String viewContact(@PathVariable (value = "cId")int cid, Model model) {
		if(userServices.getLoggingDetails()!=null) {
		Contact contact = contactService.getContact(cid);
		int u = userServices.uidByLid();
		User user=userServices.getUser(u);
		model.addAttribute("user",user);
		// set user as a model attribute to pre-populate the form
		model.addAttribute("contact", contact);
		logger.info(contact.getCName()+"is been viewed by the User");
		return "ViewContact";
		}
		else {
			logger.error("No Active User Found");
			 return "redirect:/";
		 }
	}
	
	@GetMapping("/relationContacts")
	public String searchRelation(@RequestParam("relation") String relation, Model model) {
		if(userServices.getLoggingDetails()!=null) {
			int u = userServices.uidByLid();
			User user=userServices.getUser(u);
			model.addAttribute("user",user);
		List<Contact> contacts = contactService.RealationFilter(relation,u);
		if(!(contacts.isEmpty())) {
		model.addAttribute("contacts", contacts);
		logger.info("User has filtered the contacts by"+relation );
		return "index";
	}
		else {
			model.addAttribute("user",user);
			model.addAttribute("noResult","No result Found");
			logger.info("No results found for given name");

			return "index";
		}
		}
	else {
		logger.error("No Active User Found");
		 return "redirect:/";
	 }
	}
	}

