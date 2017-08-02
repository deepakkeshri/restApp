package main.java.model;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
	
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
	
	private List<Permission> permissions;
	
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public Role(String id, List<Permission> permissions) {
		this.id = id;
		this.permissions = permissions;
	}

}
