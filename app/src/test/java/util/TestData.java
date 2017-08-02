package test.java.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.model.Permission;
import main.java.model.Role;
import main.java.model.User;

public class TestData {
	
	public static Map<String, User> users;
	public static Map<String, Role> roles;
	public static Map<String, Permission> permissions;
	
	public TestData() {
		initializeDatasource();
	}
	
	private void initializeDatasource() {
		users = new HashMap<>();
		roles = new HashMap<>();
		permissions = new HashMap<>();
		populatePermissionsData();
		populateRolesData();
		populateUsersData();
	}
	
	private void populateUsersData() {
		Map<String, List<String>> data = new HashMap<>();
		data.put("user1", new ArrayList<>(Arrays.asList("role1", "role3")));
		
		for(Map.Entry<String, List<String>> entry : data.entrySet()) {
			List<Role> usrs = new ArrayList<>();
			entry.getValue().forEach( perId -> {
				usrs.add(roles.get(perId));
			});
			users.put(entry.getKey(), new User(entry.getKey(), usrs));
		}
	}
	
	private void populateRolesData() {
		
		Map<String, List<String>> data = new HashMap<>();
		data.put("role1", new ArrayList<>(Arrays.asList("perm1", "perm5")));
		data.put("role3", new ArrayList<>(Arrays.asList("perm6", "perm7")));
		
		for(Map.Entry<String, List<String>> entry : data.entrySet()) {
			List<Permission> perms = new ArrayList<>();
			entry.getValue().forEach( perId -> {
				perms.add(permissions.get(perId));
			});
			roles.put(entry.getKey(), new Role(entry.getKey(), perms));
		}
		
	}
	
	private void populatePermissionsData() {
		
		Map<String, String> data = new HashMap<>();
		data.put("perm1", "Can check balance");
		data.put("perm5", "Can deposit");
		data.put("perm6", "Can Transfer");
		data.put("perm7", "Can withdraw");
		
		for(Map.Entry<String, String> entry : data.entrySet()) {
			permissions.put(entry.getKey(), (new Permission(entry.getKey(), entry.getValue())));
		}	
	}

}
