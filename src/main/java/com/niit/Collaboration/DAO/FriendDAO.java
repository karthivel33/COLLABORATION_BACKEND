package com.niit.Collaboration.DAO;

import java.util.List;

import com.niit.Collaboration.Entity.Friend;



public interface FriendDAO {
	
public boolean save(Friend friend); 
	
	public boolean update(Friend friend);
	
	public boolean delete(int userID,int friendID);
	
	public List<Friend> getMyFriends(int userID);
	
	public Friend getName(String name);
	
	public List<Friend> getMyNewFriendRequest(int userID);

	public void setOnline(int userID);
	
	public void setOffline(int userID);

    public Friend get(int userID,int friendID);
     
}

