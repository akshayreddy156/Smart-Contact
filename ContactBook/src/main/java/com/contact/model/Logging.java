package com.contact.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Logging {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int lid;

@Column(name = "lPhoneNumber")
private long lPhoneNumber;

@Column(name = "lpasswd")
private String lpasswd;

@OneToOne
@JoinColumn(name="user_Id")
private User luser;
}
