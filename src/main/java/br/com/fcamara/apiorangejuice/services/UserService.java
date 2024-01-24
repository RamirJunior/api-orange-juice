package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.domain.dto.UserRequest;
import br.com.fcamara.apiorangejuice.domain.entity.User;
import br.com.fcamara.apiorangejuice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public User saveUser(UserRequest userRequest) {
        var user = convertToUser(userRequest);
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private User convertToUser(UserRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }
}
