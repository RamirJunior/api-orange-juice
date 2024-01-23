package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.domain.entity.User;
import br.com.fcamara.apiorangejuice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Find all function
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Find by ID function
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Save User
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Delete by ID
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }




}
