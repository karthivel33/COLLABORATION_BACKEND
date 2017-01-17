package com.niit.Collaboration.DAO;

import java.util.List;

import com.niit.Collaboration.Entity.Job;
import com.niit.Collaboration.Entity.JobApplication;



public interface JobDAO {

	public boolean save(Job job); //posting a job

	public boolean update(Job job); //updating job
	
	public boolean update(JobApplication jobApplication);

	public Job get(int jobID);

	public JobApplication get(int jobID,int userID);
	
	public JobApplication getMyAppliedJobs(int userID);
	
	public List<Job> getAllVacantJobs();
	
	public List<Job> list();
	
	public List<JobApplication> listAllApp();
	
	public boolean updateJobApplication(JobApplication jobApplication);
	
	public boolean applyForJob(JobApplication jobApplication);
	
	public JobApplication getJobApplication(int jobID);

}
