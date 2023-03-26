package kz.fitness.homefit.security.details;


import kz.fitness.homefit.models.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@RequiredArgsConstructor
@Slf4j
public class UserDetailsImpl implements UserDetails {
    private final Account user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleAsAuthority = user.getRole().name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleAsAuthority);
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsNotBanned();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsNotBanned();
    } // Поменять в будущщем на confirm not confirm

    public Account getUser() {
        return user;
    }

}
