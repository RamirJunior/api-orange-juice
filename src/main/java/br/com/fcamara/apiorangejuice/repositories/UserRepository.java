package br.com.fcamara.apiorangejuice.repositories;

import br.com.fcamara.apiorangejuice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
