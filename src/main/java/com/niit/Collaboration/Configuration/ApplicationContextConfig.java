package com.niit.Collaboration.Configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.Collaboration.DAO.BlogDAO;
import com.niit.Collaboration.DAO.EventDAO;
import com.niit.Collaboration.DAO.ForumDAO;
import com.niit.Collaboration.DAO.FriendDAO;
import com.niit.Collaboration.DAO.JobDAO;
import com.niit.Collaboration.DAO.UserDAO;
import com.niit.Collaboration.DAOImpl.BlogDAOImpl;
import com.niit.Collaboration.DAOImpl.EventDAOImpl;
import com.niit.Collaboration.DAOImpl.ForumDAOImpl;
import com.niit.Collaboration.DAOImpl.FriendDAOImpl;
import com.niit.Collaboration.DAOImpl.JobDAOImpl;
import com.niit.Collaboration.DAOImpl.UserDAOImpl;
import com.niit.Collaboration.Entity.BaseDomain;
import com.niit.Collaboration.Entity.Blog;
import com.niit.Collaboration.Entity.BlogComment;
import com.niit.Collaboration.Entity.Event;
import com.niit.Collaboration.Entity.EventComment;
import com.niit.Collaboration.Entity.Forum;
import com.niit.Collaboration.Entity.ForumComment;
import com.niit.Collaboration.Entity.Friend;
import com.niit.Collaboration.Entity.Job;
import com.niit.Collaboration.Entity.JobApplication;
import com.niit.Collaboration.Entity.User;
import com.niit.Collaboration.Entity.Profile;





@Configuration
@ComponentScan("com.niit.Collaboration")
@EnableTransactionManagement
public class ApplicationContextConfig {

	@Bean(name = "dataSource")
	public DataSource getOracleDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");

		dataSource.setUsername("SYS AS SYSDBA");
		dataSource.setPassword("MANAGER");
		return dataSource;
	}

	private Properties getHibernateProperties() {

		Properties connectionProperties = new Properties();

		connectionProperties.setProperty("hibernate.show_sql", "true");
		connectionProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		connectionProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		connectionProperties.setProperty("hibernate.format_sql", "true");
		connectionProperties.setProperty("hibernate.jdbc.use_get_generated_keys", "true");
		// dataSource.setConnectionProperties(connectionProperties);
		return connectionProperties;
	}

	@Autowired // automatically bean is created n injected
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		
		sessionBuilder.addProperties(getHibernateProperties());
		sessionBuilder.addAnnotatedClass(User.class);
		sessionBuilder.addAnnotatedClass(Blog.class);
		sessionBuilder.addAnnotatedClass(BaseDomain.class);
		sessionBuilder.addAnnotatedClass(BlogComment.class);
		
		sessionBuilder.addAnnotatedClass(Event.class);
		sessionBuilder.addAnnotatedClass(EventComment.class);
		sessionBuilder.addAnnotatedClass(ForumComment.class);
		sessionBuilder.addAnnotatedClass(Forum.class);
		sessionBuilder.addAnnotatedClass(Friend.class);
		sessionBuilder.addAnnotatedClass(Job.class);
		sessionBuilder.addAnnotatedClass(Profile.class);

		sessionBuilder.addAnnotatedClass(JobApplication.class);
		return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}

	@Autowired
	@Bean(name = "user")
	public User getUser() {
		return new User();
	}
	@Autowired
	@Bean(name = "profile")
	public Profile getProfile() {
		return new Profile();
	}
	
	
	


	@Autowired
	@Bean(name = "userDAO")
	public UserDAO getUserDAO(SessionFactory sessionFactory) {
		return new UserDAOImpl(sessionFactory);
	}

	
	@Autowired
	@Bean(name = "blogDAO")
	public BlogDAO getBlogDAO(SessionFactory sessionFactory) {
		return new BlogDAOImpl(sessionFactory);
	}

	@Autowired
	@Bean(name = "jobDAO")
	public JobDAO getJobDAO(SessionFactory sessionFactory) {
		return new JobDAOImpl(sessionFactory);
	}


	@Autowired
	@Bean(name = "eventDAO")
	public EventDAO getEventDAO(SessionFactory sessionFactory) {
		return new EventDAOImpl(sessionFactory);
	}

	@Autowired
	@Bean(name = "forumDAO")
	public ForumDAO getForumDAO(SessionFactory sessionFactory) {
		return new ForumDAOImpl(sessionFactory);
	}
	
	@Autowired
	@Bean(name = "friendDAO")
	public FriendDAO getFriendDAO(SessionFactory sessionFactory) {
		return new FriendDAOImpl(sessionFactory);
	}

}
