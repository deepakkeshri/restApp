# restApp
Rest app using jersey and jackson in java.

Sample requests.

GET http://localhost:8080/v1/permission/checkpermission?userId=user1&permissionId=perm1
DELETE http://localhost:8080/v1/permission/perm6
GET http://localhost:8080/v1/role/role1
PUT http://localhost:8080/v1/role/role1 
{
	"permissions" : ["perm1"]
}
