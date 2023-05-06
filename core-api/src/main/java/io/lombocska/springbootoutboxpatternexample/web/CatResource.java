package io.lombocska.springbootoutboxpatternexample.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.lombocska.springbootoutboxpatternexample.service.CatService;
import io.lombocska.springbootoutboxpatternexample.web.dto.CatDTO;
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
@RequestMapping("/api/cats")
@RequiredArgsConstructor
public class CatResource {

    private final CatService service;

    @PostMapping
    public ResponseEntity create(@RequestBody CatDTO cat) throws JsonProcessingException {
        service.save(cat);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<CatDTO> cats = service.findAll();
        return ResponseEntity.ok(cats);
    }
}
