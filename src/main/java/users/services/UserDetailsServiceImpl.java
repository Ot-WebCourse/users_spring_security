package users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import users.models.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersService usersService;
	
	 @Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			// TODO Auto-generated method stub
		 User user = usersService.getUserByLogin(username);
		 
			if(user == null) {
				throw new UsernameNotFoundException("User with login " + username + " was not found");
			}
	
			return user;
		}
	

}
