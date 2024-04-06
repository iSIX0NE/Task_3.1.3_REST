package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
   private final UserRepository userRepository;
   private final RoleService roleService;
   private final RoleRepository roleRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void createUser(User user) {
        if (user.getUsername().equals("")|user.getPassword().equals("")){
            throw new UsernameNotFoundException("Пользователь не имеет пароля и логина!");
        }else{
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
        }

    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user1 = userRepository.findByUsername(username);

        if (user1.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");
        return user1.get();
    }

    @Override
    public User getUserAndRoles(User user, String[] roles) {
        if(roles==null){
            user.setRoles(roleService.getRoleByName(new String[]{"ROLE_USER"}));
        }else{
            user.setRoles(roleService.getRoleByName(roles));
        }
        return user;
    }

    @Override
    public User getNotNullRole(User user) {
        if (user.getRoles()==null){
            user.setRoles(Collections.singleton(new Role(1L)));
        }
        return user;
    }


    @PostConstruct
    public void  addUser() {
        roleRepository.save(new Role(1L,"ROLE_USER"));
        roleRepository.save(new Role(2L,"ROLE_ADMIN"));
        userRepository.save(new User(1L,"Kolyan","Sosnov",24,"user",
                "$2a$12$x63hucEmh4sAR.HbJMwzS.GK6mifBXZSKplXqF9t6PNoUpBRw9GaS",
                roleService.getRoleByName(new String[]{"ROLE_USER"})));
        userRepository.save(new User(2L,"Tolyan","Dubov",12,"admin",
                "$2a$12$/3H7Dwz98M5dkp5q/O1U4.0egk4.PF6BwzmWHJlrLl0SpcTZqp8Ja",
                roleService.getRoleByName(new String[]{"ROLE_ADMIN"})));

    }
}
