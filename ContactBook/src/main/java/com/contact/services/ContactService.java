package com.contact.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.contact.model.Contact;

@Service
public interface ContactService {
	
	List<Contact> getAllContacts();
	Contact getContactByCid(int cid);
	void deleteContactByCId(int cid);
	void saveContact(Contact contact);
	Contact getContact(int cid);
	//void insertuserIdByLid(int user_id);
	List<Contact> contactByLid(int id);
	void contactDelete(int cid);
	List<Contact> searchPrefix(String prefix,int cuid);
	List<Contact> RealationFilter(String relation,int cuid);

}
