package lt.techin.excursion_backend.service;

import lt.techin.excursion_backend.model.User;
import lt.techin.excursion_backend.repository.RoleRepository;
import lt.techin.excursion_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lt.techin.excursion_backend.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        Role defaultRole = roleRepository.findByName("ROLE_CLIENT").orElseThrow(() -> new UsernameNotFoundException("Role not found"));

        user.setRoles(Set.of(defaultRole));

        userRepository.save(user);

        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUserName(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean verifyAccountPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
