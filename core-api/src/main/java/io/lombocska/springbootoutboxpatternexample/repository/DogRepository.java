package io.lombocska.springbootoutboxpatternexample.repository;

import io.lombocska.springbootoutboxpatternexample.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
