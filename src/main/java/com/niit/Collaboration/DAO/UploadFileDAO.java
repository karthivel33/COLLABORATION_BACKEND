package com.niit.Collaboration.DAO;

import com.niit.Collaboration.Entity.UploadFile;

public interface UploadFileDAO {

	void save(UploadFile uploadFile);

	UploadFile getFile(String userName);
}


