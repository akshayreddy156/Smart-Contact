package com.contact.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.contact.model.Logging;

import jakarta.transaction.Transactional;

@Repository
public interface LoggerRepository extends JpaRepository<Logging, Integer>{
	
	@Modifying
    @Transactional
    @Query(
            value = "insert into logging(l_phone_number,lpasswd,user_id) values(?,?,?)",
            nativeQuery = true
    )
    int insertLogging(long lPhoneNumber,String passwd,int user_id);
	
	@Modifying
    @Transactional
    @Query(
            value = "Truncate table logging",nativeQuery = true)
	void loggingDelete();
	
	@Query(value="select l.user_id from logging l where l.lid=1",nativeQuery = true)
	int findByLid();
	
	@Query(value="select * from logging  ",nativeQuery = true)
	Logging getLogg();
	
	
}
