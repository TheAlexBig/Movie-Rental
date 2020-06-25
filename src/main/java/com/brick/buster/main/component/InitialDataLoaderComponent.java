package com.brick.buster.main.component;

import com.brick.buster.main.domain.auth.Privilege;
import com.brick.buster.main.domain.auth.Role;
import com.brick.buster.main.domain.auth.User;
import com.brick.buster.main.service.auth.PrivilegeServiceImp;
import com.brick.buster.main.service.auth.RoleServiceImp;
import com.brick.buster.main.service.auth.UserServiceImp;
import com.brick.buster.main.service.auth.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class InitialDataLoaderComponent implements ApplicationListener<ContextRefreshedEvent> {

    private final Boolean alreadySetup = false;

    private final UserServiceImp userServiceImp;
    private final RoleServiceImp roleServiceImp;
    private final PrivilegeServiceImp privilegeServiceImp;

    @Autowired
    public InitialDataLoaderComponent(UserServiceImp userServiceImp, RoleServiceImp roleServiceImp, PrivilegeServiceImp privilegeServiceImp) {
        this.userServiceImp = userServiceImp;
        this.roleServiceImp = roleServiceImp;
        this.privilegeServiceImp = privilegeServiceImp;
    }


    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent po) {
        if(alreadySetup)
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");

        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);

        Role adminDataRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);

        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));

        User adminUser = new User("admintest@gmail.com","admintest", "admintest", adminDataRole);
        Optional<User> userFound = userServiceImp.findByEmail("admintest@gmail.com");

        if(!userFound.isPresent()){
            userServiceImp.save(adminUser);
        }

    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {
        Optional<Privilege> privilege  = privilegeServiceImp.findByName(name);
        return privilege.orElseGet(() -> privilegeServiceImp.save(name));
    }

    @Transactional
    public Role createRoleIfNotFound(String name, List<Privilege> privileges) {
        Optional<Role> role  = roleServiceImp.findByName(name);

        return role.orElseGet(() -> roleServiceImp.save(name, privileges));
    }
}
