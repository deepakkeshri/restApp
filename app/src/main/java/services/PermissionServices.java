package main.java.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import main.java.exception.InputException;
import main.java.model.Permission;
import main.java.model.Role;

public class PermissionServices extends GenericService {
	
	public JSONObject checkUserPermission(JerseyServiceManager services, String userId, String permId) throws Exception {
		List<Permission> permissions = services.getUserServices().getUserPermissions(userId);
		JSONObject res = new JSONObject();
		Permission permission = getPermission(permId);
		Boolean hasPermission = false;
		for(Permission perm : permissions) {
			if(perm.getId().equals(permission.getId())) {
				hasPermission = true;
				break;
			}
		}
		res.put("hasPermission", hasPermission);
		return res;
	}
	
	public List<Permission> getPermission(List<String> permIds) throws Exception {
		List<Permission> permissions = new ArrayList<>();
		Permission permission;
		for(String id : permIds) {
			permission = getPermission(id);
			permissions.add(permission);
		}
		return permissions;
	}
	
	public Permission getPermission(String id) throws Exception {
		Permission permission = null;
		if(id == null) {
			throw new InputException("301", "Invalid permission id");
		}
		DataServices datasource = DataServices.getConnection();
		permission = datasource.getPermissions().get(id);
		if(permission == null) {
			throw new InputException("302", "Invalid permission id");
		}
		return permission;
	}
	
	private Boolean removePermission(String id) {
		Boolean isRemoved = false;
		DataServices datasource = DataServices.getConnection();
		datasource.deletePermission(id);
		isRemoved = true;
		return isRemoved;
	}
	
	public JSONObject deletePermission(JerseyServiceManager services, String id) throws Exception {
		List<Role> roles = services.getRoleServices().getRolesFromPermission(id);
		if(roles.isEmpty()) {
			throw new InputException("303", "Permission can not be deleted as it has role associated with it");
		}
		removePermission(id);
		JSONObject res = new JSONObject();
		res.put("success", true);
		return res;
	}

}
