package com.example.diplomaanahit.mapper;

import com.example.diplomaanahit.dtos.QuestionAndVariantsForLecturerDTO;
import com.example.diplomaanahit.dtos.QuestionVariantsStudentDTO;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionVariantsMapper {

    public List<QuestionVariantsStudentDTO> getQuestionVariantsDTOList(List<QuestionVariantsEntity> entities, Boolean available){
        List<QuestionVariantsStudentDTO> dtos = new ArrayList<>();
        for (QuestionVariantsEntity entity : entities) {
            QuestionVariantsStudentDTO dto = getQuestionVariantsDTO(entity, available);
            dtos.add(dto);
        }
        return  dtos;
    }

    private QuestionVariantsStudentDTO getQuestionVariantsDTO(QuestionVariantsEntity entity, Boolean available) {
        QuestionVariantsStudentDTO dto = new QuestionVariantsStudentDTO();
        dto.setId(entity.getId());
        dto.setQuestion(entity.getQuestion());
        dto.setVariant1(entity.getVariant1());
        dto.setVariant2(entity.getVariant2());
        dto.setVariant3(entity.getVariant3());
        return dto;
    }

    public List<QuestionVariantsEntity> getQuestionVariantsEntityList(List<QuestionAndVariantsForLecturerDTO> questions, Lesson lesson) {
        List<QuestionVariantsEntity> entities = new ArrayList<>();
        for (QuestionAndVariantsForLecturerDTO dto : questions) {
            QuestionVariantsEntity entity = getQuestionVariantsEntity(dto, lesson);
            entities.add(entity);
        }
        return entities;
    }

    private QuestionVariantsEntity getQuestionVariantsEntity(QuestionAndVariantsForLecturerDTO dto, Lesson lesson) {
        QuestionVariantsEntity entity = new QuestionVariantsEntity();
        entity.setLesson(lesson);
        entity.setQuestion(dto.getQuestion());
        entity.setVariant1(dto.getVariant1());
        entity.setVariant2(dto.getVariant2());
        entity.setVariant3(dto.getVariant3());
        if(dto.getCorrectVariant().equals(dto.getVariant1())){
            entity.setNumber(1);
        }
        else if(dto.getCorrectVariant().equals(dto.getVariant2())){
            entity.setNumber(2);
        }
        else if(dto.getCorrectVariant().equals(dto.getVariant3())){
            entity.setNumber(3);
        }
        return entity;
    }
}
