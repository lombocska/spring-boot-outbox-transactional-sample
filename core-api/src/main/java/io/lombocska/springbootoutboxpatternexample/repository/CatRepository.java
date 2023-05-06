package io.lombocska.springbootoutboxpatternexample.repository;

import io.lombocska.springbootoutboxpatternexample.domain.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Long> {
}
