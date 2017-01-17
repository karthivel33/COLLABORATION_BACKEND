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

import com.niit.Collaboration.DAO.BlogDAO;
import com.niit.Collaboration.Entity.Blog;
import com.niit.Collaboration.Entity.BlogComment;
import com.niit.Collaboration.Entity.User;



@RestController
public class BlogController {

private static final Logger logger	= LoggerFactory.getLogger(BlogController.class);
	
	@Autowired
	BlogDAO blogDAO;
	
	@Autowired
	Blog blog;
	
	
	@RequestMapping(value="/blogs",method=RequestMethod.GET)
	public ResponseEntity<List<Blog>> listAllBlogs(){
		logger.debug("calling method listAllBlogs");
		List<Blog> blog=blogDAO.list();
		if(blog.isEmpty()){
			return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Blog>>(blog,HttpStatus.OK);
	}

	@RequestMapping(value="/blog/",method=RequestMethod.POST)
	public ResponseEntity<Blog> createBlog(@RequestBody Blog blog,HttpSession httpSession){
	
		Date dt=new java.util.Date();
		blog.setCreation_date(dt.toString());
		User user=(User) httpSession.getAttribute("loggedInUser");
		blog.setUser_name(user.getUser_name());
		blog.setStatus("Published");
		if(blogDAO.save(blog)==true){
			blog.setErrorCode("200");
			blog.setErrorMessage("blog posted");			
		}else{
		logger.debug("blog cannot be posted");
		blog.setErrorCode("400");
		blog.setErrorMessage("blog cannot be posted");
		return new ResponseEntity<Blog>(blog,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Blog>(blog,HttpStatus.OK);
			}
 	
	@RequestMapping(value="/blog/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Blog> updateBlog(@PathVariable("id") int blog_id,@RequestBody Blog blog){
		logger.debug("calling method updateBlog" + blog.getBlog_id());
		if(blogDAO.get(blog_id)==null){
			logger.debug("blog does not exists with id:" + blog.getBlog_id());		
			blog=new Blog();
			blog.setErrorMessage("blog does not exists with id:" + blog.getBlog_id());
			return new ResponseEntity<Blog> (blog,HttpStatus.NOT_FOUND);
		}
		blogDAO.update(blog);
		logger.debug("blog updated successfully");
		return new ResponseEntity<Blog> (blog,HttpStatus.OK);		
	}

	@RequestMapping(value="/blog/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Blog> deleteBlog(@PathVariable("id") int blog_id){
		logger.debug("calling method deleteBlog for blog id: " + blog_id);
		Blog blog=blogDAO.get(blog_id);
		if(blog==null){
			logger.debug("blog does not exists with id:" + blog_id);
			blog=new Blog();
			blog.setErrorMessage("blog does not exists with id:" + blog_id);
			return new ResponseEntity<Blog> (blog,HttpStatus.NOT_FOUND);	
		}
		blogDAO.delete(blog);
		logger.debug("blog deleted successfully");
		return new ResponseEntity<Blog> (blog,HttpStatus.OK);		
	}
	
	@RequestMapping(value="/blog/{id}",method=RequestMethod.GET)
	public ResponseEntity<Blog> getBlog(@PathVariable("id") int blog_id){
		logger.debug("calling method getBlog for blog id: " + blog_id);
		Blog blog=blogDAO.get(blog_id);
		if(blog==null){
			logger.debug("blog does not exists with id:" + blog_id);
			blog=new Blog();
			blog.setErrorMessage("blog does not exists with id:" + blog_id);
			return new ResponseEntity<Blog> (blog,HttpStatus.NOT_FOUND);
		}
		logger.debug("blog exists with id:" + blog_id);
		return new ResponseEntity<Blog> (blog,HttpStatus.OK);
	}


	@RequestMapping(value="/blogcomment/{id}",method=RequestMethod.POST)
	public ResponseEntity<BlogComment> createBlogComment(@PathVariable("id") int blog_id,@RequestBody BlogComment blogcomment,HttpSession httpSession){
		logger.debug("calling method createBlogComment" + blogcomment.getBlogid());
		Integer loogedInUserID=(Integer) httpSession.getAttribute("loggedInUserID");
		User username=(User) httpSession.getAttribute("loggedInUser");
		blogcomment.setUser_id(loogedInUserID);
		Date dt=new java.util.Date();
		blogcomment.setCommentDate(dt.toString());
		blogcomment.setUser_name(username.getUser_name());
		blogcomment.setBlogid(blog_id);
		if(blogDAO.addComment(blogcomment)==true){
			blogcomment.setErrorCode("200");
			blogcomment.setErrorMessage("Comment saved");
		
		}else{
			return new ResponseEntity<BlogComment>(blogcomment,HttpStatus.BAD_REQUEST);
		}
				
		return new ResponseEntity<BlogComment>(blogcomment,HttpStatus.OK);
			}

	@RequestMapping(value="/blogscommentlistperblog/{id}",method=RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> listAllBlogsCommentsPerBlog(@PathVariable("id") int blog_id){
		logger.debug("calling method listAllBlogs");
		List<BlogComment> blogcomment=blogDAO.listComment(blog_id);
		if(blogcomment.isEmpty()){
			return new ResponseEntity<List<BlogComment>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BlogComment>>(blogcomment,HttpStatus.OK);
	}


	@RequestMapping(value="/blogscommentlist/",method=RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> listAllBlogsComments(){
		logger.debug("calling method listAllBlogs");
		List<BlogComment> blogcomment=blogDAO.listOfAllComment();
		if(blogcomment.isEmpty()){
			return new ResponseEntity<List<BlogComment>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BlogComment>>(blogcomment,HttpStatus.OK);
	}

}



