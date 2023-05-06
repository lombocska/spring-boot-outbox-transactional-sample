package io.lombocska.springbootoutboxpatternexample.web.dto;

import io.lombocska.springbootoutboxpatternexample.domain.Dog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DogDTO {

    private String name;
    private int age;

    public static Dog toEntity(DogDTO dto) {
        return Dog.builder().name(dto.getName()).age(dto.getAge()).build();
    }

    public static DogDTO toDTO(Dog entity) {
        return DogDTO.builder().name(entity.getName()).age(entity.getAge()).build();
    }
}
