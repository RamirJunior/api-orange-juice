package br.com.fcamara.apiorangejuice.repositories;

import br.com.fcamara.apiorangejuice.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
