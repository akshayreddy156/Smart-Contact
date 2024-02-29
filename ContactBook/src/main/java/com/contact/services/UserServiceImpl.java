package com.contact.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.contact.Repository.LoggerRepository;
import com.contact.Repository.UserRepository;
import com.contact.exceptions.EmailAlreadyExistException;
import com.contact.exceptions.PhoneNumberAlreadyExistException;
import com.contact.model.Logging;
import com.contact.model.User;
@Service
public  class UserServiceImpl implements UserServices{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LoggerRepository loggerRepository;

	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void saveUser(User user) throws PhoneNumberAlreadyExistException,EmailAlreadyExistException{
		if(userRepository.findByPhoneNumber(user.getPhoneNumber())!=null) {
			throw new PhoneNumberAlreadyExistException("The Given Phone Number is already in Use . Please Enter Another Phone Number");
		}
		if(userRepository.findByUemail(user.getUemail())!=null) {
			throw new EmailAlreadyExistException("The Given Email is already in Use . Please Enter Another Email");
		}
		this.userRepository.save(user);
	}
	
	@Override
	public User getUser(int uid) {
		Optional<User> optional =userRepository.findById(uid);
		User user = null;
		if (optional.isPresent()) {
			user = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + uid);
		}
		return user;
	}

	@Override
	public void deleteUserByUid(int uid) {
		this.userRepository.deleteById(uid);
	}

	public boolean validateCredentials(long phn, String Password) {
		return userRepository.findByPhoneNumberAndPasswd(phn, Password)!=null;
		
	}

	@Override
	public int insertLogging(long lPhoneNumber, String passwd,int user_id) {
		return loggerRepository.insertLogging(lPhoneNumber, passwd,user_id);
	}

	

	@Override
	public void loggerDeleteAll() {
		this.loggerRepository.deleteAll();
		System.out.println("deleted");
	}

	@Override
	public int userByPhoneNumber(long phoneNumber) {
		return userRepository.userByPhoneNumber(phoneNumber);
	}

	@Override
	public User findByPhoneNumberAndPasswd(long phoneNumber, String pwd) {
		
		return userRepository.findByPhoneNumberAndPasswd(phoneNumber, pwd);
	}

	@Override
	public void loggingDelete() {
		this.loggerRepository.loggingDelete();
		
	}
	public int uidByLid(){
		return loggerRepository.findByLid();
	}

	@Override
	public Logging getLoggingDetails() {
		// TODO Auto-generated method stub
		return loggerRepository.getLogg();
	}
	
	@Override
	public User forgottenPassword(long phn, String dob) {
		return userRepository.forgotPasswd(phn, dob);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		this.userRepository.save(user);
		
	}

//	@Override
//	public Logging getLoggingDetails() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public void insertuserIdByLid(int user_id) {
//		 this.contactRepository.insertuserIdC(user_id);
//	}
	
//	public Logging findByLphoneNumber(long phnnum) {
//		return	loggerRepository.findByLPhoneNumber(phnnum);
//		
//	}
	
}
