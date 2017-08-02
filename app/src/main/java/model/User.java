package main.java.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public User(String id, List<Role> roles) {
		this.id = id;
		this.roles = roles;
	}
	

}
