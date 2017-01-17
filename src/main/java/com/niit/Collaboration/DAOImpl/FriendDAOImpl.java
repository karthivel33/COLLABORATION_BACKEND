package com.niit.Collaboration.DAOImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Collaboration.DAO.FriendDAO;
import com.niit.Collaboration.Entity.Friend;



@SuppressWarnings("deprecation")
@Repository(value = "FriendDAO")
public class FriendDAOImpl implements FriendDAO {
	
	private static final Logger logger	= LoggerFactory.getLogger(FriendDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	public FriendDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Transactional
	public boolean save(Friend friend) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean update(Friend friend) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().update(friend);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(int userID,int friendID) {
		// TODO Auto-generated method stub
		try {
			Friend friend=new Friend();
			friend.setFriend_id(friendID);
			friend.setUser_id(userID);
			sessionFactory.getCurrentSession().delete(friendID);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public List<Friend> getMyFriends(int userID) {
		// TODO Auto-generated method stub
		String hql = "from Friend where user_id=" + userID + "and request_status= '" + "A'";
		@SuppressWarnings("rawtypes")
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Friend> list = (List<Friend>) query.list();

		return list;
	}

	@Transactional
	public Friend getName(String name) {
		// TODO Auto-generated method stub
		String hql = "from Friend where userID=" + "'" + name + "'";
		@SuppressWarnings("rawtypes")
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Friend> list = (List<Friend>) query.list();

		if (list != null && !list.isEmpty()) {
			System.out.println("username retrieved from DAOImpl");
			return list.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public List<Friend> getMyNewFriendRequest(String userID){
		String hql = "from Friend where userID=" +  userID + " and request_status = '" + "N'";
		@SuppressWarnings("rawtypes")
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Friend> list = (List<Friend>) query.list();
        return list;
		
	}


	@Transactional
	public List<Friend> getMyNewFriendRequest(int userID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Transactional
	public void setOnline(int userID) {
		logger.debug("Starting of method setOnline");
		String hql="UPDATE Friend SET isOnline='Y' where friend_id=" + userID;
		logger.debug("hql: " + hql);
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		logger.debug("Ending of method setOnline");
	}


	@Transactional
	public void setOffline(int userID) {
		logger.debug("Starting of method setOffline");
		String hql="UPDATE Friend SET isOnline='N' where friend_id=" + userID;
		logger.debug("hql: " + hql);
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		logger.debug("Ending of method setOffline");
	}


	@Transactional
	public Friend get(int userID, int friendID) {
		String hql="from Friend where user_id=" + userID + "and friend_id=" +  friendID ;
	logger.debug("hql: " + hql);
	Query query=sessionFactory.getCurrentSession().createQuery(hql);
	List<Friend> list = (List<Friend>) query.list();

	if (list != null && !list.isEmpty()) {
		System.out.println("friend retrieved from DAOImpl");
		return list.get(0);
	} else {
		return null;
	}
}

		}
