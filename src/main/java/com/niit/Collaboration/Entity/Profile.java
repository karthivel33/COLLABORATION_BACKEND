package com.niit.Collaboration.Entity;

import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

public class Profile {

	
	@Transient
	private MultipartFile image;

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
