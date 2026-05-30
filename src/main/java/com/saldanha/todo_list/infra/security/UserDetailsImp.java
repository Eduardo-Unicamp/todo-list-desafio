package com.saldanha.todo_list.infra.security;

import com.saldanha.todo_list.entity.User;
import com.saldanha.todo_list.entity.UserRole;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Data
public class UserDetailsImp implements UserDetails {
    private User user = new User();

    public UserDetailsImp(User user){
        this.user=user;
    }
    public UserDetailsImp(String username, String password, UserRole role){
        this.user.setUsername(username);
        this.user.setPassword(password);
        this.user.setRole(role);

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(user.getRole()==UserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"));
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
}
