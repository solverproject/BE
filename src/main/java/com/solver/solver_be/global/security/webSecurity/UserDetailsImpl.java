package com.solver.solver_be.global.security.webSecurity;

import com.solver.solver_be.domain.user.entity.Admin;
import com.solver.solver_be.domain.user.entity.Guest;
import com.solver.solver_be.domain.user.entity.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final Admin admin;
    private final Guest guest;
    private final String userId;

    public UserDetailsImpl(Admin admin, String userId) {
        this.admin = admin;
        this.guest = null;
        this.userId = userId;
    }

    public UserDetailsImpl(Guest guest, String userId) {
        this.admin = null;
        this.guest = guest;
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = (admin != null) ? admin.getRole() : guest.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    public Admin getAdmin() {
        return this.admin;
    }

    public Guest getGuest() {
        return this.guest;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}