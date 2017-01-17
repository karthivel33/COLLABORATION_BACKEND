package com.niit.Collaboration.DAOImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Collaboration.DAO.JobDAO;
import com.niit.Collaboration.Entity.Job;
import com.niit.Collaboration.Entity.JobApplication;


@SuppressWarnings("deprecation")
@Repository(value = "JobDAO")
public class JobDAOImpl implements JobDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public JobDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean save(Job job) {
		// TODO Auto-generated method stub
		try {
			//job.setJob_id(getMaxID()+1);
			sessionFactory.getCurrentSession().save(job);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//ALTERNATIVE FOR SEQUENCE
	private int getMaxID(){
		String hql="select max(job_id) from Job ";
		return (Integer) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
		
	}

	@Transactional
	public boolean update(Job job) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().update(job);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public Job get(int jobID) {
		// TODO Auto-generated method stub
		String hql = "from Job where job_id=" + jobID;

		@SuppressWarnings("rawtypes")
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Job> list = (List<Job>) query.list();

		if (list != null && !list.isEmpty()) {
			System.out.println("job retrieved from DAOImpl");
			return list.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public List<Job> list() {
		// TODO Auto-generated method stub
		String hql = " from Job";
		@SuppressWarnings("rawtypes")
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();

	}

	@Transactional
	public List<Job> getAllVacantJobs() {
		String hql = " from Job where status= 'V' ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	@Transactional
	public boolean applyForJob(JobApplication jobApplication) {
		try {
			sessionFactory.getCurrentSession().save(jobApplication);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	public boolean updateJobApplication(JobApplication jobApplication) {
		try {
			sessionFactory.getCurrentSession().update(jobApplication);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	public JobApplication get(int jobID, int userID) {
		String hql = " from JobApplication where user_id= " + userID + "' and job_id= '" + jobID;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (JobApplication) query.list();
		}
	
		
	@Transactional
	public JobApplication getMyAppliedJobs(int userID) {
		String hql = " from Job where job_id in(select job_id from JobApplication where user_id=" + userID + ")";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (JobApplication) query.list();
	}

	@Transactional
	public JobApplication getJobApplication(int jobID) {
		String hql = "from JobApplication where job_id=" + jobID;

		@SuppressWarnings("rawtypes")
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<JobApplication> list = (List<JobApplication>) query.list();

		if (list != null && !list.isEmpty()) {
			System.out.println("job retrieved from DAOImpl");
			return list.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public boolean update(JobApplication jobApplication) {
		// TODO Auto-generated method stub
		try {
			//job.setJob_id(getMaxID()+1);
			sessionFactory.getCurrentSession().save(jobApplication);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public List<JobApplication> listAllApp() {
		// TODO Auto-generated method stub
		String hql = " from JobApplication";
		@SuppressWarnings("rawtypes")
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();

	}

	

}

