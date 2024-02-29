package com.contact.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="User_Login")
public class User {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int uid;

	@Column(name = "phoneNumber",unique = true)
	private long phoneNumber;

	@Column(name = "first_name")
	private String uName;
	
	@Column(name = "passwd")
	private String passwd;
	
	@Column(name = "uemail",unique = true)
	private String uemail;
	
	private String birthDay;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Contact> contacts;
	
	@OneToOne(mappedBy ="luser",cascade = CascadeType.ALL)
	private Logging logging;
	
}
