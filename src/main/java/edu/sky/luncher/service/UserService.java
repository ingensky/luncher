package edu.sky.luncher.service;

import edu.sky.luncher.domain.Role;
import edu.sky.luncher.domain.User;
import edu.sky.luncher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }


    public User addUser(User user) {
        return addCommonUser(user, Collections.singleton(Role.USER));
    }

    public User addAdmin(User user) {
        return addCommonUser(user, new HashSet<>(Arrays.asList(Role.ADMIN, Role.USER)));

    }

    private User addCommonUser(User user, Set<Role> roles) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return null;
        }
        user.setRoles(roles);
        return userRepository.save(user);

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void update(User user, Long id) throws Exception {
        if (!user.getId().equals(id)) {
            throw new Exception("wrong id");
        }
        userRepository.save(user);
    }

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
