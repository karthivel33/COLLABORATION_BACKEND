package com.niit.Collaboration.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Collaboration.DAO.FriendDAO;
import com.niit.Collaboration.DAO.UserDAO;
import com.niit.Collaboration.Entity.Friend;
import com.niit.Collaboration.Entity.User;



@RestController
public class FriendController {

	private static final Logger logger = LoggerFactory.getLogger(FriendController.class);

	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;

	@Autowired
	Friend friend;

	@Autowired
	FriendDAO friendDAO;

	@RequestMapping(value = "/friends/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> listAllFriends(@PathVariable("id") int userID) {
		logger.debug("calling method listAllFriends");
		List<Friend> friend = friendDAO.getMyFriends(userID);
		if (friend.isEmpty()) {
			return new ResponseEntity<List<Friend>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Friend>>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/addFriend/{friendID}", method = RequestMethod.POST)
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendID") int friendID, HttpSession session) {
		logger.debug("calling method sendFriendRequest");
		Integer loogedInUserID=(Integer) session.getAttribute("loggedInUserID");
		user = userDAO.get(friendID);
		friend.setUser_id(loogedInUserID);
		friend.setFriend_id(friendID);
		friend.setFriend_name(user.getUser_name());
		friend.setRequest_status('N');
		friendDAO.save(friend);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/friend/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> updateFriend(@PathVariable("id") int friend_id, @RequestBody Friend friend) {
		logger.debug("calling method updateFriend" + friend.getFriend_id());
		if (friendDAO.getMyFriends(friend_id) == null) {
			logger.debug("friend does not exists with id:" + friend.getFriend_id());
			friend = new Friend();
			friend.setErrorMessage("friend does not exists with id:" + friend.getFriend_id());
			return new ResponseEntity<Friend>(friend, HttpStatus.NOT_FOUND);
		}
		friendDAO.update(friend);
		logger.debug("friend updated successfully");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	
	 
	 
	@RequestMapping(value = "/myFriends", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getmyFriends(HttpSession session) {
		logger.debug("calling method getmyFriends ");
		int loggedInUserID = (Integer) session.getAttribute("loggedInUserID");
		List<Friend> myFriends = friendDAO.getMyFriends(loggedInUserID);
		return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);
	}

	private void updateRequest(int friendID, Character status, HttpSession session) {
		int loggedInUserID = (Integer) session.getAttribute("loggedInUserID");
		friend.setUser_id(loggedInUserID);
		friend.setFriend_id(friendID);
		friend.setRequest_status(status);
		friendDAO.update(friend);
	}

	@RequestMapping(value = "unFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> unFriend(@PathVariable("friendID") int friendID, HttpSession session) {
		logger.debug("calling method unFriend ");
		updateRequest(friendID, 'U', session);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "rejectFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> rejectFriend(@PathVariable("friendID") int friendID, HttpSession session) {
		logger.debug("calling method rejectFriend ");
		updateRequest(friendID, 'R', session);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/acceptFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> acceptFriendRequest(@PathVariable("friendID") int friendID, HttpSession session) {
		logger.debug("calling method acceptFriend ");
		updateRequest(friendID, 'A', session);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMyFriendRequests", method = RequestMethod.GET)
	public ResponseEntity<Friend> getMyFriendRequests(HttpSession session) {
		logger.debug("calling method getMyFriendRequests ");
		int loggedInUserID = (Integer) session.getAttribute("loggedInUserID");
		friendDAO.getMyNewFriendRequest(loggedInUserID);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

}
