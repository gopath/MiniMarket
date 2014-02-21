package ac.id.itb.d4.minimart.costumer.model;

public class Employee {

	private String username;
	private String password;
	private String employeeId;
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getEmployeeId(){
		return employeeId;
	}
	
	public void setEmployeeId(String id){
		this.employeeId = id;
	}
}
