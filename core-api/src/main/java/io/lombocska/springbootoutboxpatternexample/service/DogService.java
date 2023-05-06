package io.lombocska.springbootoutboxpatternexample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lombocska.springbootoutboxpatternexample.domain.Dog;
import io.lombocska.springbootoutboxpatternexample.repository.DogRepository;
import io.lombocska.springbootoutboxpatternexample.web.dto.DogDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DogService {

    private final DogRepository dogRepository;
    private final MessageChannel dogChannel;
    private final ObjectMapper om;

    @Transactional(readOnly = false)
    public void save(DogDTO dto) throws JsonProcessingException {
        final Dog dog = dogRepository.save(DogDTO.toEntity(dto));
        GenericMessage<String> message = new GenericMessage<>(om.writeValueAsString(dog));
        dogChannel.send(message);
        log.info("Dog creation process has been finishing with id {} and message header uuid {}", dog.getId(), message.getHeaders().getId());
    }

    public List<DogDTO> findAll(){
        return dogRepository.findAll().stream().map(DogDTO::toDTO).collect(Collectors.toList());
    }
}
