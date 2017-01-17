package com.niit.Collaboration.DAO;

import java.util.List;

import com.niit.Collaboration.Entity.Blog;
import com.niit.Collaboration.Entity.BlogComment;



public interface BlogDAO {

	public boolean save(Blog blog); 
	
	public boolean update(Blog blog);
	
	public boolean delete(Blog blog);
	
	public Blog get(int blogID);
	
	public Blog getName(String name);
	
	public List<Blog> list();
	
	public boolean addComment(BlogComment blogcomment);
	
	public List<BlogComment> listComment(int id);
	
	public List<BlogComment> listOfAllComment();
	

}
