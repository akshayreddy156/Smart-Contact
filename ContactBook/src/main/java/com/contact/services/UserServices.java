package com.contact.services;

import java.util.List;

import com.contact.exceptions.EmailAlreadyExistException;
import com.contact.exceptions.PhoneNumberAlreadyExistException;
import com.contact.model.Logging;
import com.contact.model.User;

public interface UserServices {
	List<User> getAllUsers();
	void saveUser(User user) throws PhoneNumberAlreadyExistException,EmailAlreadyExistException;
	User getUser(int uid);
	void deleteUserByUid(int uid);
	int userByPhoneNumber(long phoneNumber);
	User findByPhoneNumberAndPasswd(long phoneNumber,String pwd);
	boolean validateCredentials(long phn, String Password);
	User forgottenPassword(long phn,String dob);
	void updateUser(User user);
	
	void loggingDelete();
	int insertLogging(long lPhoneNumber,String passwd,int user_id);
	//void deleteByLPhoneNumber(long phoneNumber);
	void loggerDeleteAll();
	Logging getLoggingDetails();
	int uidByLid();
	//Logging findByLphoneNumber(long phnnum);
}
