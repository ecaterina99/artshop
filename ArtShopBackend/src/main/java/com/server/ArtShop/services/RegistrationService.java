package com.server.ArtShop.services;


import com.server.ArtShop.exceptions.UserAlreadyExistsException;
import com.server.ArtShop.models.User;
import com.server.ArtShop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        String password = passwordEncoder.encode(user.getPassword());
        user.setRole("ROLE_USER");
        user.setPassword(password);
        return userRepository.save(user);

    }
}
