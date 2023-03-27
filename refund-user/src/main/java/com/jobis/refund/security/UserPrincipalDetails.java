package com.jobis.refund.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jobis.refund.domain.User.entity.SzsUser;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipalDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private SzsUser szsUser;

	// 일반 시큐리티 로그인시 사용
	public UserPrincipalDetails(SzsUser szsUser) {
		this.szsUser = szsUser;
	}

	public SzsUser getUser() {
		return szsUser;
	}

	@Override
	public String getPassword() {
		return szsUser.getPassword();
	}

	@Override
	public String getUsername() {
		return szsUser.getUserId();
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		// collet.add(()->{ return szsUser.getAut().getAutId();});
		return collet;
	}

}
