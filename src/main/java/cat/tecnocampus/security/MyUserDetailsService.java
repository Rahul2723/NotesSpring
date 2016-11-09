package cat.tecnocampus.security;

import cat.tecnocampus.exceptions.UserLabNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by roure on 27/10/2016.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserSecurityRepository userSecurityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserLabNotFoundException {

        UserSecurity user = userSecurityRepository.findOne(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}