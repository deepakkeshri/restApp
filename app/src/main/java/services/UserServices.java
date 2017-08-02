package main.java.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import main.java.exception.InputException;
import main.java.model.Permission;
import main.java.model.Role;
import main.java.model.User;

public class UserServices extends GenericService {
	
	public JSONObject getUserPermissionNames(String userId) throws Exception {
		List<Permission> permissions = getUserPermissions(userId);
		List<String> names = new ArrayList<>();
		JSONObject res = new JSONObject();
		for(Permission permission : permissions) {
			names.add(permission.getName());
		}
		res.put("permissions", names);
		return res;
	}
	
	public List<Permission> getUserPermissions(String userId) throws Exception {
		User user = getUser(userId);
		List<Role> roles = user.getRoles();
		List<Permission> permissions = new ArrayList<>();
		for(Role role : roles) {
			permissions.addAll(role.getPermissions());
		}
		return permissions;
	}
	
	public User getUser(String userId) throws Exception {
		
		if(userId == null || userId == "") {
			throw new InputException("101", "invalid id");
		}
		
		User user = null;
		DataServices datasource = DataServices.getConnection();
		
		user = datasource.getUsers().get(userId);
		
		if(user == null) {
			throw new InputException("102", "No user found with this id");
		}
		return user;
	}

}
