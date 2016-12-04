package users;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import users.models.Post;
import users.models.RegistrationForm;
import users.services.PostsService;
import users.services.UsersService;

    @Controller
    public class IndexController {
   
    	
    	@Autowired
        private PostsService postsService;
    	
    	@Autowired
    	private UsersService usersService;
    	
        @RequestMapping(value = "/post", method = RequestMethod.POST)
        public String createPost(@RequestParam("text") String postText) {
        	//TODO
        	postsService.addPost(new Post("Unknown", postText, new Date()));
            return "redirect:home";
        }
        
        /*public void addPost(String text) {
		User currentUser = usersRepo.findOne(User.getCurrentUserId());
		postsRepo.save(new Post(currentUser, text, new Date()));
	}*/
    	
        @RequestMapping("/home")
        public String index(Model model) {
            model.addAttribute("posts", postsService.getRecentPosts());
            return "index";
        }  
   
     
        

    }