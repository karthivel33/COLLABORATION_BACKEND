package com.niit.Collaboration.DAOImpl;

	import org.hibernate.Query;
	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Repository;
	import org.springframework.transaction.annotation.Transactional;

import com.niit.Collaboration.DAO.UploadFileDAO;
import com.niit.Collaboration.Entity.UploadFile;

	
	
	@Repository
	public class UploadFileDAOImpl implements UploadFileDAO {

		@Autowired
		private SessionFactory sessionFactory;

		@Transactional
		public void save(UploadFile uploadFile) {
			Session session = sessionFactory.openSession();
			session.save(uploadFile);
			session.flush();
			session.close();

		}

		public UploadFile getFile(String userName) {
			// TODO Auto-generated method stub
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from UploadFile where userName = ?");
			query.setParameter(0, userName);
			UploadFile uploadFile = (UploadFile) query.uniqueResult();
			session.close();
			return uploadFile;

		}

	}

