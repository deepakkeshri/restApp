package test.java.services;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import main.java.exception.InputException;
import main.java.model.Permission;
import main.java.model.Role;
import main.java.services.DataServices;
import main.java.services.JerseyServiceManager;
import main.java.services.PermissionServices;
import main.java.services.RoleServices;
import main.java.services.UserServices;
import test.java.util.TestData;

@PrepareForTest(DataServices.class)
@RunWith(PowerMockRunner.class)
public class RoleServicesTest {
	
	@Mock private UserServices mUserServices;
	@Mock private RoleServices mRoleServices;
	@Mock private PermissionServices mPermissionServices;
	@Mock private DataServices mDataServices;
	public TestData mTestData;
	
	@Before
	public void setup() {
		mRoleServices = new RoleServices();
		mTestData = new TestData();
	
		mUserServices = Mockito.mock(UserServices.class);	
		mDataServices = Mockito.mock(DataServices.class);
		mPermissionServices = Mockito.mock(PermissionServices.class);
		
		when(mDataServices.getRoles()).thenReturn(TestData.roles);
		PowerMockito.mockStatic(DataServices.class);
	    PowerMockito.when(DataServices.getConnection())
	         .thenReturn(mDataServices);
	}
	
	@Test
	public void testGetRole() {
		String id = "role1";
		Role role = null;
		try {
			role = mRoleServices.getRole(id);
		} catch(Exception e) {
			fail("should not fail " + e);
		}
		Assert.assertNotNull(role);
	}
	
	@Test
	public void testGetRoleNullId() throws Exception {
		String id = null;
		Role role = null;
		try {
			role = mRoleServices.getRole(id);
		} catch(InputException e) {
			Assert.assertEquals("201", e.getErrorCode());
		}
		Assert.assertNull(role);
	}
	
	@Test
	public void testGetRoleInvalidId() throws Exception {
		String id = "invalid";
		Role role = null;
		try {
			role = mRoleServices.getRole(id);
		} catch(InputException e) {
			Assert.assertEquals("201", e.getErrorCode());
		}
		Assert.assertNull(role);
	}
	
	@Test
	public void testUpdateRole() throws Exception {
		JerseyServiceManager services = Mockito.mock(JerseyServiceManager.class);
		when(services.getPermissionServices()).thenReturn(mPermissionServices);
		Map<String, Object> input = new HashMap<>();
		List<String> permIds = new ArrayList<>();
		permIds.add("perm1");
		input.put("permissions", permIds);
		String id = "role1";
		List<Permission> perms = new ArrayList<>();
		perms.add(TestData.permissions.get(id));
		when(mPermissionServices.getPermission(permIds)).thenReturn(perms);
		JSONObject res = mRoleServices.updateRole(services, input, id);
		
		
		Assert.assertEquals(res.get("success"), true);
	}
	
	@Test
	public void testGetRolesFromPermission() {
		String permId = "perm1";
		List<Role> roles = mRoleServices.getRolesFromPermission(permId);
		Assert.assertNotNull(roles);
		Assert.assertNotNull(roles.get(0).getId(), "role1");
	}
	
	@Test
	public void testGetRolesFromPermissionInvalidId() {
		String permId = "invalid";
		List<Role> roles = mRoleServices.getRolesFromPermission(permId);
		Assert.assertNotNull(roles);
		Assert.assertEquals(roles.size(), 0);
	}

}
