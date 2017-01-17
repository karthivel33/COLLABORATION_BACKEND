package com.niit.Collaboration.Controllers;

import java.util.Date;
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

import com.niit.Collaboration.DAO.EventDAO;
import com.niit.Collaboration.Entity.Event;
import com.niit.Collaboration.Entity.User;



@RestController
public class EventController {
	
private static final Logger logger	= LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	EventDAO eventDAO;
	
	@RequestMapping(value="/events",method=RequestMethod.GET)
	public ResponseEntity<List<Event>> listAllEvents(){
		logger.debug("calling method listAllEvents");
		List<Event> event=eventDAO.list();
		if(event.isEmpty()){
			return new ResponseEntity<List<Event>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Event>>(event,HttpStatus.OK);
	}

	@RequestMapping(value="/event/",method=RequestMethod.POST)
	public ResponseEntity<Event> createEvent(@RequestBody Event event,HttpSession httpSession){
		logger.debug("calling method createEvent" + event.getEvent_id());
		if(eventDAO.get(event.getEvent_id())==null){
			Date dt=new java.util.Date();
			event.setCreation_date(dt.toString());
			User user=(User) httpSession.getAttribute("loggedInUser");
			event.setUser_name(user.getUser_name());
			eventDAO.save(event);			
		}
		logger.debug("event already exists with id:" + event.getEvent_id());
		event.setErrorMessage("event already exists with id:" + event.getEvent_id());
		return new ResponseEntity<Event>(event,HttpStatus.OK);
			}
	
	@RequestMapping(value="/event/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Event> updateEvent(@PathVariable("id") int event_id,@RequestBody Event event){
		logger.debug("calling method updateEvent" + event.getEvent_id());
		if(eventDAO.get(event_id)==null){
			logger.debug("event does not exists with id:" + event.getEvent_id());		
			event=new Event();
			event.setErrorMessage("event does not exists with id:" + event.getEvent_id());
			return new ResponseEntity<Event> (event,HttpStatus.NOT_FOUND);
		}
		eventDAO.update(event);
		logger.debug("event updated successfully");
		return new ResponseEntity<Event> (event,HttpStatus.OK);		
	}

	@RequestMapping(value="/event/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Event> deleteEvent(@PathVariable("id") int event_id){
		logger.debug("calling method deleteEvent for event id: " + event_id);
		Event event=eventDAO.get(event_id);
		if(event==null){
			logger.debug("event does not exists with id:" + event_id);
			event=new Event();
			event.setErrorMessage("event does not exists with id:" + event_id);
			return new ResponseEntity<Event> (event,HttpStatus.NOT_FOUND);	
		}
		eventDAO.delete(event);
		logger.debug("event deleted successfully");
		return new ResponseEntity<Event> (event,HttpStatus.OK);		
	}
	
	@RequestMapping(value="/event/{id}",method=RequestMethod.GET)
	public ResponseEntity<Event> getEvent(@PathVariable("id") int event_id){
		logger.debug("calling method getEvent for event id: " + event_id);
		Event event=eventDAO.get(event_id);
		if(event==null){
			logger.debug("event does not exists with id:" + event_id);
			event=new Event();
			event.setErrorMessage("event does not exists with id:" + event_id);
			return new ResponseEntity<Event> (event,HttpStatus.NOT_FOUND);
		}
		logger.debug("event exists with id:" + event_id);
		return new ResponseEntity<Event> (event,HttpStatus.OK);
	}


}



