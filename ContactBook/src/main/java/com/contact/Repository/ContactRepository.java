package com.contact.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.model.Contact;
import jakarta.transaction.Transactional;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

//	@Modifying
//    @Transactional
//    @Query(
//            value = "insert into contact_list(cid, birth_day, c_email, c_name, c_phone_number, n_name, relation, user_uid) values(?1)",
//            nativeQuery = true
//    )
//    void insertuserIdC(int user_id);
	
	@Query(value="select * from contact_list  where cuid=? ",nativeQuery = true)
	List<Contact> findByLid(int id);
	
	void deleteBycId(int cid);
	
  @Modifying
  @Transactional
  @Query(
          value = "DELETE FROM contact_list WHERE cid = (?1)",
          nativeQuery = true)
 void jdeleteContact(int cid);
	//List<Contact> findByCNameContainingAndUser(String keyword,User user);
  
	/*
	 * @Query(value="select * from contact_list  where cName  ",nativeQuery = true)
	 * List<Contact> findByCNameStartingWith(String prefix);
	 */
  
  @Query(value = "Select * from contact_list Where relation=:relation And cuid=:cuid",nativeQuery = true)
  List<Contact> filterByRealation(@Param("relation") String relation,@Param("cuid") int id);
  
  
  @Query(value="SELECT * FROM contact_list  WHERE(c_name LIKE CONCAT(:prefix, '%') OR c_name LIKE CONCAT('%', :prefix)) And cuid=:cuid",nativeQuery=true)
  List<Contact> findByPrefix(@Param("prefix") String prefix,@Param("cuid") int id);
}
