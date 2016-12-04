package users.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;


import users.models.User;
import users.models.UserRoleEnum;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;


@Service
public class UsersService {
	
	private List<User> users = new ArrayList<User>();
	
	@PostConstruct
	public void createAdminUser() {		
		//register("admin", "admin@mail.com", "qwerty");
		register("admin", "admin@mail.com", "qwerty",UserRoleEnum.ADMIN);
		
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public Boolean register(String login, String email, String pass,UserRoleEnum role) {
		String passHash = new BCryptPasswordEncoder().encode(pass);
		
		User u = new User(login, email.toLowerCase(), passHash,role);
		u.setAuthorities(); //for Spring Security
		if(users.add(u)){
			return true;
		}
		return false;
	}
	
	 public User getUserByLogin(String login) {
	    	List<User> allUsers=getUsers();
	    	
	    	for (User u : allUsers){
	    		if(u.getLogin().equalsIgnoreCase(login))
	    		{
	    			return u;
	    		}
	         	
	    	}
	    	
	    	return null;
	     }
	 

	    public User login(String login, String password) {
	        
	            User user = getUserByLogin(login);
	            user.setAuthorities(); //for Spring Security
	           // if(user == null || !user.getPassword().equals(password)) { //without hash
	            if(user==null || !( new BCryptPasswordEncoder().matches(password, user.getPassword()) ) ){
	            	 
	            	return null;
	            }
	            
	            return user;
	    }
	

	    public String getUserLocation(String userAgent) {  
	    	 UserAgent userAgentStr = UserAgent.parseUserAgentString(userAgent);
	    	    Browser browser = userAgentStr.getBrowser();
	    	    String browserName = browser.getName();
	    	    //or 
	    	    // String browserName = browser.getGroup().getName();
	    	    Version browserVersion = userAgentStr.getBrowserVersion();
	    	    OperatingSystem os =  userAgentStr.getOperatingSystem();
	    	    String[] osArr=os.toString().split("_");
	    	    return "The user is using browser " + browserName + " - version " + browserVersion + ". Operating System - " + osArr[0] + " " + osArr[1];
	    	    //System.out.println("The user is using browser " + browserName + " - version " + browserVersion);
	            
	    }

		
	

	
}
