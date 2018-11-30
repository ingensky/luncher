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

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User addUser(User user) {
        return addCommonUser(user, Collections.singleton(Role.ROLE_USER));
    }

    public User addAdmin(User user) {
        return addCommonUser(user, new HashSet<>(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER)));

    }

    private User addCommonUser(User user, Set<Role> roles) {
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
