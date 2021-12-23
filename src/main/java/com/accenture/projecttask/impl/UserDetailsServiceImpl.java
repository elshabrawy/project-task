package com.accenture.projecttask.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<String> roles =null;//load data for sys user only

		List<String> amminRoles=List.of("ROLE_ADMIN");
		List<String> userRoles=List.of("ROLE_USER");
		if(userName.equals("ahmed")) {
			roles=amminRoles;
		}else {
			roles=userRoles;
		}
		
        Collection<? extends GrantedAuthority> authorities = this.getAuthorities(roles);
        return new User(userName, "*******", true, true, true, true, authorities);


	}
	private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {

        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (final String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }
	
}
