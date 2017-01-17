package com.niit.Collaboration.Controllers;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.Collaboration.DAO.UploadFileDAO;
import com.niit.Collaboration.DAO.UserDAO;
import com.niit.Collaboration.Entity.UploadFile;
import com.niit.Collaboration.Entity.User;



@RestController
public class FileUploadController {

	@Autowired
	private UploadFileDAO uploadFileDAO;

	@Autowired
	private UploadFile uploadFile;


          @Autowired
          UserDAO userDAO;
  
	@RequestMapping(value = "/doUpload", method = RequestMethod.POST)
	public ResponseEntity<?> handleFileUpload(HttpServletRequest request, HttpSession session,
			@RequestParam CommonsMultipartFile fileUpload) throws Exception {
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		System.out.println("getting friends of: " + loggedInUserId);
		
		User user = userDAO.getByemailId(loggedInUserId);
		System.out.println("User emailId: " + user.getEmail());
		if (user == null)
			throw new RuntimeException("Not logged in");
		System.out.println("User is " + user.getUser_name());
		if (fileUpload != null) {
			CommonsMultipartFile file = fileUpload;
			System.out.println("Saving File: " + file.getOriginalFilename());
			UploadFile uploadFile = new UploadFile();
			uploadFile.setFileName(file.getOriginalFilename());
			uploadFile.setUserName(user.getEmail());
			uploadFile.setData(file.getBytes()); // image
			uploadFileDAO.save(uploadFile);
			

			UploadFile getUploadFile = uploadFileDAO.getFile(user.getEmail());
			String name = getUploadFile.getFileName();
			System.out.println("FileName: " + name);
			System.out.println("File: " + getUploadFile.getData());
			byte[] imagefiles = getUploadFile.getData(); // image

			try {
				String path = "E://AmiconBackEnd/src/main/webapp/WEB-INF/resources/images/"
						+ user.getEmail();
				System.out.println("Path: " + path);
				File files = new File(path);
				FileOutputStream fos = new FileOutputStream(files);
				fos.write(imagefiles);
				fos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return new ResponseEntity<UploadFile>(HttpStatus.OK);
	}

	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	public ResponseEntity<?> getFile(HttpSession session) {
		User user = (User) session.getAttribute("loggedInUserId");
		UploadFile uploadFile = uploadFileDAO.getFile(user.getEmail());
		String name = uploadFile.getFileName();
		System.out.println("Name: " + name);
		System.out.println("File: " + uploadFile.getData());
		byte[] imagefiles = uploadFile.getData();
		return new ResponseEntity<byte[]>(imagefiles, HttpStatus.OK);
	}
}
