package com.kidaro.kael.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kidaro.kael.model.User;
import com.kidaro.kael.repository.UserRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;

    public Optional<User> authenticate(String username, String password) {
        return userRepo.findByUsernameAndPassword(username, password);
    }
}
