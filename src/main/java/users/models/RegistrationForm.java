package users.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class RegistrationForm {
	
	@NotNull
	@Size(min = 4, max = 100)
	private String login;
	
	
	@Email
	@Size(max = 512)
	private String email;
	
	@NotNull
	@Size(min = 6, max = 9)
	private String pass;
	
	@Min(16)
	private Integer age;

	@NotNull
	@Size(min = 2, max = 2)
	private String country;
	
	public RegistrationForm() {}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
