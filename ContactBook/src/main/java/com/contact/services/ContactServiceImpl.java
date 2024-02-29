package com.contact.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.Repository.ContactRepository;
import com.contact.model.Contact;
@Service
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public Contact getContactByCid(int cid) {
		Optional<Contact> optional = contactRepository.findById(cid);
		Contact contact = null;
		if (optional.isPresent()) {
			contact = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + cid);
		}
		return contact;
	}
	
	@Override
	public List<Contact> getAllContacts() {
		
		return contactRepository.findAll();
	}
	
	@Override
	public void deleteContactByCId(int cid) {
		this.contactRepository.deleteBycId(cid);
	}

	@Override
	public void saveContact(Contact contact) {
		this.contactRepository.save(contact);	
	}

	@Override
	public Contact getContact(int cid) {
		contactRepository.findById(cid);
		Optional<Contact> optional =contactRepository.findById(cid);
		Contact contact = null;
		if (optional.isPresent()) {
			contact = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + cid);
		}
		return contact;
	}
	
	public List<Contact> contactByLid(int id){
		return contactRepository.findByLid(id);
	}

	@Override
	public void contactDelete(int cid) {
		this.contactRepository.jdeleteContact(cid);
	}

	@Override
	public List<Contact> searchPrefix(String prefix,int cuid) {
		return contactRepository.findByPrefix(prefix,cuid);
	}
	@Override
	public List<Contact> RealationFilter(String relation,int cuid) {
		return contactRepository.filterByRealation(relation,cuid);
	}
}
