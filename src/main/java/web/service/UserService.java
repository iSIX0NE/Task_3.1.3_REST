package web.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;

import java.util.List;


public interface UserService extends UserDetailsService {
    List<User> getAllUser();

    void createUser(User user);

    void deleteUser(Long id);

    User  getUserById(Long id);
    User getUserAndRoles(User user, String[] roles);
    User getNotNullRole(User user);


}
