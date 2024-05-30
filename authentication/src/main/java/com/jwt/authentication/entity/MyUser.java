package com.jwt.authentication.entity;

import com.jwt.authentication.custom_annotation.StrongPassword;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = "email")
})
public class MyUser {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Size(min = 4, message="username should be atleast 4 characters long")
    private String name;
	@Email(message="Enter a valid email")
    private String email;
	@StrongPassword
    private String password;
	private boolean isLoggedOut = false;
    private String roles;
    
    
	public boolean isLoggedOut() {
		return isLoggedOut;
	}
	public void setLoggedOut(boolean isLoggedOut) {
		this.isLoggedOut = isLoggedOut;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoles() {
		return roles;
	}
	

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public MyUser() {
		super();
	}
	@Override
	public String toString() {
		return "MyUser [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", isLoggedOut="
				+ isLoggedOut + ", roles=" + roles + "]";
	}
	public MyUser(int id, @Size(min = 4, message = "username should be atleast 4 characters long") String name,
			@Email(message = "Enter a valid email") String email, String password, boolean isLoggedOut, String roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.isLoggedOut = isLoggedOut;
		this.roles = roles;
	}

	

}
