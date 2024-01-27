package br.com.fcamara.apiorangejuice.repositories;

import br.com.fcamara.apiorangejuice.domain.entity.Project;
import br.com.fcamara.apiorangejuice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByUser(User user);
    List<Project> findByDeleted(boolean value);
}
