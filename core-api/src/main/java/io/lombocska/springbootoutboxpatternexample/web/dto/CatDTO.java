package io.lombocska.springbootoutboxpatternexample.web.dto;

import io.lombocska.springbootoutboxpatternexample.domain.Cat;
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
public class CatDTO {

    private String name;
    private int age;

    public static Cat toEntity(CatDTO dto) {
        return Cat.builder().name(dto.getName()).age(dto.getAge()).build();
    }

    public static CatDTO toDTO(Cat entity) {
        return CatDTO.builder().name(entity.getName()).age(entity.getAge()).build();
    }
}
