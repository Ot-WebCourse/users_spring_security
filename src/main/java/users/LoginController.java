package users;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import users.models.Post;
import users.models.RegistrationForm;
import users.models.User;
import users.models.UserRoleEnum;
import users.services.PostsService;
import users.services.UsersService;

@Controller
public class LoginController {

	@Autowired
	private UsersService usersService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET) //Spring Security automatically redirect user to the root "/" after authorization
    public String redirectAfterSecurityLogin(HttpSession session) { 
    	//Object l = session.getAttribute("userLogin");
    	User curUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	//if(l!=null){
    	if(curUser!=null){
        	return "redirect:user-profile/"+curUser.getLogin();
        }
    	return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(HttpSession session) {
    	//Object l = session.getAttribute("userLogin");
    	
    	//String l = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //anonymousUser
    	// якщо користувач вже увійшов, то перекинути його на власну сторінку		
    	if(!User.isAnonymous()) {
        	User curUser = User.getCurrentUser(); // (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
        	return "redirect:user-profile/"+curUser.getLogin();
        }
    	return "login";
    	
    }
    
    //NOT USED Processed by Spring Security
   /* @RequestMapping(value = "/logout", method = RequestMethod.GET)  //doesn't used now
    public String logout(HttpSession session) {
    	session.removeValue("userLogin");
        //session.invalidate();
    	
    	return "redirect:/login";
    }*/
    
   
  //NOT USED Processed by Spring Security     
    /* @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("login") String login,@RequestParam("password") String password, Model model, HttpSession session) {
        User user=usersService.login(login, password);
      
        if(user!=null){
        	//session.setAttribute("userId", userId);
        	//session.setAttribute("userLogin", login);

        	return "redirect:user-profile/"+login;
        	
        }
        else{
        	model.addAttribute("error_msg","Wrong login or password!");
        	
        	return "login";
        }

    }*/
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String regForm(RegistrationForm formData, Model model) {
    	model.addAttribute("form", formData);
    	
    	return "register";
    }
    
     	@RequestMapping(value = "/register", method = RequestMethod.POST)
public String register(@Valid RegistrationForm formData, BindingResult bindResult, Model model, HttpSession session) {
	
	if(bindResult.hasErrors()) {
		model.addAttribute("errors", bindResult.getFieldErrors());
		model.addAttribute("form", formData);
		return "register"; //registration form
	}
	//Boolean registered=usersService.register(formData.getLogin(), formData.getEmail(), formData.getPass());
	Boolean registered=usersService.register(formData.getLogin(), formData.getEmail(), formData.getPass(),UserRoleEnum.USER);
	if(registered){
		//session.setAttribute("userLogin",formData.getLogin());
		
		//This request requires authorization => by default redirect to Login Form
    	//return "redirect:user-profile/"+formData.getLogin(); 
		
		//That's why better at once redirect to Login Form URL
    	return "redirect:login";
	}
	
	return "register"; //registration form
}

     	
}
