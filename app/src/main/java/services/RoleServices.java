package main.java.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import main.java.exception.InputException;
import main.java.model.Permission;
import main.java.model.Role;

public class RoleServices extends GenericService {
	
	@SuppressWarnings("unchecked")
	public JSONObject updateRole(JerseyServiceManager services, Map<String, Object> input, String id) throws Exception {
		
		if(input == null) {
			throw new InputException("204", "Invalid input");
		}
		
		//role to be updated
		Role role = getRole(id);
		
		//get input permission ids
		List<String> inputPermIds = new ArrayList<>();
		List<String> perms;
		try {
			
			if(input.get("permissions") == null) {
				throw new InputException("205", "Invalid input. 'permissions' array is required");
			}
			
			if(!(input.get("permissions") instanceof List)) {
				throw new InputException("206", "permissions property should be array");
			}
			
			perms = (List<String>) input.get("permissions");
			for(int index = 0; index < perms.size(); index++) {
				inputPermIds.add(perms.get(index));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//get permissions from ids
		List<Permission> permissions = services.getPermissionServices().getPermission(inputPermIds);
		
		//update permissions for this role
		role.setPermissions(permissions);
		
		//save role
		saveRole(role);
		JSONObject res = new JSONObject();
		res.put("success", true);
		return res;
	}
	
	public Role getRole(String id) throws Exception {
		Role role = null;
		DataServices datasource = DataServices.getConnection();
		role = datasource.getRoles().get(id);
		
		if(role == null) {
			throw new InputException("201", "Invalid role id");
		}
		
		return role;
	}
	
	public JSONObject getRoleData(String id) throws Exception {
		Role role = getRole(id);
		
		JSONObject res = new JSONObject();
		res.put("id", role.getId());
		List<String> permissions = new ArrayList<>();
		for(Permission per : role.getPermissions()) {
			permissions.add(per.getId());
		}
		res.put("permissions", permissions);
		return res;
	}
	
	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<>();
		DataServices datasource = DataServices.getConnection();
		Map<String, Role> map = datasource.getRoles();
		for(Map.Entry<String, Role> e : map.entrySet()) {
			roles.add(e.getValue());
		}
		return roles;
	}
	
	
	public List<Role> getRolesFromPermission(String permId) {
		List<Role> roles = getRoles();
		List<Role> requiredRoles = new ArrayList<>();
		List<Permission> permissions;
		for(Role role : roles) {
			permissions = role.getPermissions();
			permissions.forEach(perm -> {
				if(perm.getId().equals(permId)) {
					requiredRoles.add(role);
				}
			});
		}
		return requiredRoles;
	}
	
	public void saveRole(Role role) {
		DataServices datasource = DataServices.getConnection();
		datasource.saveRole(role);
	}

}
