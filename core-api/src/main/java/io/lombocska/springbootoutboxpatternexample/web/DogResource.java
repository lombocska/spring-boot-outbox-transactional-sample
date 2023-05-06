package io.lombocska.springbootoutboxpatternexample.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.lombocska.springbootoutboxpatternexample.service.DogService;
import io.lombocska.springbootoutboxpatternexample.web.dto.DogDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/dogs")
@RequiredArgsConstructor
public class DogResource {

    private final DogService service;

    @PostMapping
    public ResponseEntity create(@RequestBody DogDTO dog) throws JsonProcessingException {
        service.save(dog);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<DogDTO> cats = service.findAll();
        return ResponseEntity.ok(cats);
    }
}
