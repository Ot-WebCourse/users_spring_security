package users.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import users.models.User;


public class User implements UserDetails  {
	
	private Long id;
	
	private String login;
	
	private String email;

	private String password;
	
	private Boolean hasAvatar;
	
	private UserRoleEnum role;
	
	private Set<GrantedAuthority> authRoles = new HashSet<GrantedAuthority>();
	
	
	private List<Post> posts = new ArrayList<>();
	
	public User() {
		super();
	}
	
	public User(String login, String email, String password) {
		this.login = login;
		this.email = email;
		this.password = password;
		this.password = password;
		role=UserRoleEnum.USER;
		hasAvatar=false;
	}
	
	public User(String login, String email, String password,UserRoleEnum role) {
		this.login = login;
		this.email = email;
		this.password = password;
		this.password = password;
		this.role=role;
		hasAvatar=false;
	}

	public Boolean getHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(Boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}
	
	
	public String getRole() {
		return role.name();
	}

	public void setRole(UserRoleEnum role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//return AuthorityUtils.createAuthorityList("USER");
		return AuthorityUtils.createAuthorityList(this.getRole().toString());
	}
	
	//public Set<GrantedAuthority> setAuthorities() {
	public void setAuthorities() {
	 // указываем роли для этого пользователя
		this.authRoles.add(new SimpleGrantedAuthority(this.getRole().toString()));
		//return authRoles;
	}

	@Override
	public String getUsername() {
		return getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static Long getCurrentUserId() {
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return u.getId();
	}
	
	public static String getCurrentUserName() {
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return u.getLogin();
	}
	
	public static User getCurrentUser() {
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return u;
	}
	
	public static boolean isAnonymous() {
		// Метод SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
		// нічого не дасть, оскільки анонімний користувач в Spring Security теж вважається авторизованим і має ім'я по замовчуванню anonymousUser
		return "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
