package com.niit.Collaboration.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name="USERS")
@Component
public class User extends BaseDomain
{
	
	@Id
	@GeneratedValue(generator="InvSeq") 
    @SequenceGenerator(name="InvSeq",sequenceName="USERS_SEQ", allocationSize=1) 
	private int user_id;

	@NotEmpty(message="Please fill the first name")
	private String user_name;
	@NotEmpty(message="Password is a must")
	private String password;
	//@NotEmpty(message="Confirm Password must be filled ")
	@Transient
	private String cpassword;	
	@NotEmpty(message="Please enter emailID")
	private String email;
	//@NotEmpty(message="Mobile No. should not be empty")
	private int contact;
	@NotEmpty(message="Role should not be empty")
	private String role;
	private char isOnline;	

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
		System.out.println("setting userid");
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
		System.out.println("setting user name");
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
		System.out.println("setting password");
		
	}
	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
		System.out.println("setting email");
	}
	public int getContact() {
		return contact;
	}
	public void setContact(int contact) {
		this.contact = contact;
		System.out.println("setting contact");
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
		System.out.println("setting role");
	}
	public char getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(char isOnline) {
		this.isOnline = isOnline;
	}
	
		
}

