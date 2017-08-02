package test.java.services;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import main.java.exception.InputException;
import main.java.model.Permission;
import main.java.services.DataServices;
import main.java.services.PermissionServices;
import main.java.services.RoleServices;
import main.java.services.UserServices;
import test.java.util.TestData;

public class PermissionServicesTest {
	
	@Mock private PermissionServices mPermissionServices;
	@Mock private UserServices mUserServices;
	@Mock private RoleServices mRoleServices;
	@Mock private DataServices mDataServices;
	public TestData mTestData;
	
	@Before
	public void setup() {
		mPermissionServices = new PermissionServices();
		mTestData = new TestData();
	
		mUserServices = Mockito.mock(UserServices.class);
		mRoleServices = Mockito.mock(RoleServices.class);
		mDataServices = Mockito.mock(DataServices.class);
	}
	
	@Test
	public void testGetPermission() throws Exception {
		String id = "perm1";
		Permission permission = null;
		try {
			permission = mPermissionServices.getPermission(id);
		} catch(InputException e) {
			fail("should not fail " + e);
		}
		Assert.assertNotNull(permission);
		Assert.assertEquals(id, permission.getId());
	}
	
	@Test
	public void testGetPermissionNullId() throws Exception {
		String id = null;
		Permission permission = null;
		try {
			permission = mPermissionServices.getPermission(id);
		} catch(InputException e) {
			Assert.assertEquals("301", e.getErrorCode());
		}
		Assert.assertNull(permission);
	}
	
	@Test
	public void testGetPermissionInvalidId() throws Exception {
		String id = "invalid";
		Permission permission = null;
		try {
			permission = mPermissionServices.getPermission(id);
		} catch(InputException e) {
			Assert.assertEquals("302", e.getErrorCode());
		}
		Assert.assertNull(permission);
	}

}
