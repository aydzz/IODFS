package com.pup.cea.iodfs.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.security.UserLogin;


public class CurrentUser implements UserDetails {
	
		private UserLogin userLogin;
		private final String ROLE_PREFIX = "ROLE_";
		
		private UserInfo userInfo;
		
	    public CurrentUser(UserLogin userLogin, UserInfo userInfo) {
	        this.userLogin = userLogin;
	        this.userInfo = userInfo;
	    }
	    
	    //NOT OVERRIDEN METHODS
	    public String getName() {
			return userInfo.getName();
		}


		public String getOffice() {
			return userInfo.getOffice();
		}

		public String getEmail() {
			return userInfo.getEmail();
		}


		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
	        roleList.add(new SimpleGrantedAuthority(ROLE_PREFIX + userLogin.getRole().toUpperCase())); 
	        // have to set toUpperCase because ADMIN role is lower even though caps siya sa DB
	        return roleList;
		}

		@Override
		public String getPassword() {
			return userLogin.getPassword();
		}
		@Override
		public String getUsername() {
			return userLogin.getUsername();
		}
		
		//Modify these methods later for more specific security configuration
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

}
