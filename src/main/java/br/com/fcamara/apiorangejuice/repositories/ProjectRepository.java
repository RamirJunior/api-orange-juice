package br.com.fcamara.apiorangejuice.repositories;

import br.com.fcamara.apiorangejuice.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
