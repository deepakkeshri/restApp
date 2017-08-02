package test.java.services;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
import main.java.model.User;
import main.java.services.DataServices;
import main.java.services.UserServices;
import test.java.util.TestData;

@PrepareForTest(DataServices.class)
@RunWith(PowerMockRunner.class)
public class UserServicesTest {

	@Mock private UserServices mUserServices;
	@Mock private DataServices mDataServices;
	public TestData mTestData;
	
	@Before
    public void setUp() {
		mUserServices = new UserServices();
		mTestData = new TestData();
		mDataServices = Mockito.mock(DataServices.class);
	}
	
	@Test
	public void testGetUser() throws Exception {
		String userId = "user1";
		User user = null;
		Map<String, User> users = TestData.users;
		when(mDataServices.getUsers()).thenReturn(users);
		PowerMockito.mockStatic(DataServices.class);
	    PowerMockito.when(DataServices.getConnection())
	         .thenReturn(mDataServices);
	    try {
	    	user = mUserServices.getUser(userId);
	    } catch (Exception e) {
	    	fail("Should not fail " + e);
	    }
		
		Assert.assertNotNull(user);
		Assert.assertEquals("user1", user.getId());
	}
	
	@Test
	public void testGetUserWhenNullId() throws Exception {
		String userId = null;
		User user = null;
		Map<String, User> users = TestData.users;
		when(mDataServices.getUsers()).thenReturn(users);
		PowerMockito.mockStatic(DataServices.class);
	    PowerMockito.when(DataServices.getConnection())
	         .thenReturn(mDataServices);
	    InputException exception = null;
	    try {
	    	user = mUserServices.getUser(userId);
	    } catch(InputException ex) {
	    	exception = ex;
	    }
		Assert.assertNull(user);
		Assert.assertEquals("101", exception.getErrorCode());
	}
	
	@Test
	public void testGetUserWhenInvalidId() throws Exception {
		String userId = "invalidId";
		User user = null;
		Map<String, User> users = TestData.users;
		when(mDataServices.getUsers()).thenReturn(users);
		PowerMockito.mockStatic(DataServices.class);
	    PowerMockito.when(DataServices.getConnection())
	         .thenReturn(mDataServices);
	    InputException exception = null;
	    try {
	    	user = mUserServices.getUser(userId);
	    } catch(InputException ex) {
	    	exception = ex;
	    }
		Assert.assertNull(user);
		Assert.assertEquals("102", exception.getErrorCode());
	}
	
	@Test
	public void testGetUserPermissionNames() throws Exception {
		String userId = "user1";
		JSONObject res = null;
		try {
			res = mUserServices.getUserPermissionNames(userId);
	    } catch(InputException ex) {
	    	fail("Should not fail " + ex);
	    }
		List<Role> roles = TestData.users.get(userId).getRoles();
		List<String> names = new ArrayList<>();
		for(Role role : roles) {
			List<Permission> perms = role.getPermissions();
			for(Permission perm : perms) {
				names.add(perm.getName());
			}
		}
		List<String> actualnames = new ArrayList<>();
		JSONArray arr = (JSONArray) res.get("permissions"); 
		for(int i=0; i<arr.length(); i++) {
			actualnames.add(i, arr.getString(i));
		}
		Assert.assertEquals(names, actualnames);
	}
	
}
