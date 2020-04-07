package com.brick.buster.main.service.auth;

import com.brick.buster.main.domain.auth.Privilege;
import com.brick.buster.main.domain.auth.Role;
import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Primary
@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;

    private final RoleServiceImp roleServiceImp;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository, RoleServiceImp roleServiceImp) {
        this.userRepository = userRepository;
        this.roleServiceImp = roleServiceImp;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameOrEmail(s, s);
        if(user.isPresent()){
            List<Role> roles = user.get().getRoles();
             if(roles.isEmpty()){
                    Role role = roleServiceImp.findByName("ROLE_USER").get();
                    return new org.springframework.security.core.userdetails.User
                        (s, user.get().getPassword(), getAuthorities(Collections.singletonList(role)));

            }
            else{
                    return new org.springframework.security.core.userdetails.User
                        (s, user.get().getPassword(), getAuthorities(roles));
            }
        }
        throw new UsernameNotFoundException(s);
    }

    private Collection<GrantedAuthority> getAuthorities(Collection<Role> roles){
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        privileges.forEach(it->{
            authorities.add(new SimpleGrantedAuthority(it));
        });
        return authorities;
    }

    private List<String> getPrivileges(Collection<Role> roles){
        List<String> privileges = new ArrayList<String>();
        List<Privilege> collection = new ArrayList<Privilege>();
        roles.forEach(it->{
            List<Privilege> privilege = it.getPrivileges();
            if(!privilege.isEmpty()){
                collection.addAll(privilege);
            }
        });
        collection.forEach(it ->{
            privileges.add(it.getName());
        });
        return privileges;
    }

}
