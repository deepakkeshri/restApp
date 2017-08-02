package main.java.services;

public class JerseyServiceManager {
	
	
	private UserServices mUserServices;
	
	public UserServices getUserServices() {
		return mUserServices;
	}
	
	private void setUserServices(UserServices pUserServices) {
		this.mUserServices = pUserServices;
	}
	
	private RoleServices mRoleServices;
	
	public RoleServices getRoleServices() {
		return mRoleServices;
	}
	
	private void setRoleServices(RoleServices pRoleServices) {
		this.mRoleServices = pRoleServices;
	}
	
	private PermissionServices mPermissionServices;
	
	public PermissionServices getPermissionServices() {
		return mPermissionServices;
	}
	
	private void setPermissionServices(PermissionServices pPermissionServices) {
		this.mPermissionServices = pPermissionServices;
	}
	
	public JerseyServiceManager() {
		this.setUserServices(new UserServices());
		this.setRoleServices(new RoleServices());
		this.setPermissionServices(new PermissionServices());
	}

}
