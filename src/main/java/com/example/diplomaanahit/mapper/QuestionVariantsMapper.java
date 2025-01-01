package com.example.diplomaanahit.mapper;

import com.example.diplomaanahit.dtos.QuestionVariantsDTO;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionVariantsMapper {

    public List<QuestionVariantsDTO> getQuestionVariantsDTOList(List<QuestionVariantsEntity> entities, Boolean available){
        List<QuestionVariantsDTO> dtos = new ArrayList<>();
        for (QuestionVariantsEntity entity : entities) {
            QuestionVariantsDTO dto = getQuestionVariantsDTO(entity, available);
            dtos.add(dto);
        }
        return  dtos;
    }

    private QuestionVariantsDTO getQuestionVariantsDTO(QuestionVariantsEntity entity, Boolean available) {
        QuestionVariantsDTO dto = new QuestionVariantsDTO();
        dto.setId(entity.getId());
        dto.setQuestion(entity.getQuestion());
        dto.setVariant1(entity.getVariant1());
        dto.setVariant2(entity.getVariant2());
        dto.setVariant3(entity.getVariant3());
        dto.setAvailable(available);
        return  dto;
    }
}
