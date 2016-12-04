package users;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import users.models.User;
import users.services.UsersService;

@Controller
public class UserController {
	

	@Autowired
	private UsersService usersService;
	
     	
	  @RequestMapping("/user-list")
	    public String users(Model model) {
	        model.addAttribute("users", usersService.getUsers());
	        return "users";
	    }
	  
	  @RequestMapping("user-profile/{username}")
	    public String userPage(@PathVariable String username,@RequestHeader ("User-Agent") String userAgent, Model model,HttpSession session) {
		  	//Object l = session.getAttribute("userLogin");
	          // if(l!=null){
		  		if(User.getCurrentUserName().equals(username)){
			  	User user = usersService.getUserByLogin(username);
					if(user!=null){
			            model.addAttribute("user", user);
			            if(user.getHasAvatar()==true){
			            	model.addAttribute("avatarUrl", "/image/"+user.getLogin()+"_avatar");
			            }
			            else{
			            	model.addAttribute("avatarUrl", "http://placehold.it/128x128");
			            }
			            
			            
						model.addAttribute("location", usersService.getUserLocation(userAgent));
				        
				        return "user_page";
					}
		  		}
			//}
			return "redirect:/register";
	    }
}
