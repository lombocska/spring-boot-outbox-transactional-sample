package io.lombocska.springbootoutboxpatternexample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lombocska.springbootoutboxpatternexample.domain.Cat;
import io.lombocska.springbootoutboxpatternexample.repository.CatRepository;
import io.lombocska.springbootoutboxpatternexample.web.dto.CatDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final MessageChannel catChannel;
    private final ObjectMapper om;

    @Transactional(readOnly = false)
    public void save(CatDTO dto) throws JsonProcessingException {
        final Cat cat = catRepository.save(CatDTO.toEntity(dto));
        GenericMessage<String> message = new GenericMessage<>(om.writeValueAsString(cat));
        catChannel.send(message);
        log.info("Cat creation process has been finishing with id {} and message header uuid {}", cat.getId(), message.getHeaders().getId());
    }

    public List<CatDTO> findAll(){
        return catRepository.findAll().stream().map(CatDTO::toDTO).collect(Collectors.toList());
    }
}
