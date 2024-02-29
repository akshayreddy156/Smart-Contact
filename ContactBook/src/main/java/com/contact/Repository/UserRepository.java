package com.contact.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.contact.model.User;
import jakarta.transaction.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	
	User findByPhoneNumberAndPasswd(long phoneNumber,String pwd);
	
	@Modifying
    @Transactional
	@Query(value="select s.uid from user_login s where s.phone_number = ?1",nativeQuery = true)
    int userByPhoneNumber(long phoneNumber);
	
	User findByPhoneNumber(long phoneNumber);
	
	User findByUemail(String uemail);
	
	@Query(value = "Select * from user_login Where phone_number=:phoneNumber And birth_day=:dob",nativeQuery = true)
	  User forgotPasswd(@Param("phoneNumber")long phoneNumber,@Param("dob") String dob);
	 
	@Modifying
    @Transactional
	@Query(value="select s.uid from user_login s where s.phone_number = ?1",nativeQuery = true)
    int updatePasswd(long phoneNumber);
}
