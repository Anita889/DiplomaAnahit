package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.dtos.QuestionVariantsDTO;
import com.example.diplomaanahit.entities.LecturerEntity;
import com.example.diplomaanahit.entities.LessonEntity;
import com.example.diplomaanahit.services.LecturerService;
import com.example.diplomaanahit.services.LessonService;
import com.example.diplomaanahit.services.QuestionVariantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/lecturer{lecturerId}")
public class LecturerController {
    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private QuestionVariantsService questionVariantsService;

    @RequestMapping(value = "lesson/{lessonId}/listOfQuestions", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestionsOfAvailableLesson(@PathVariable int lecturerId, @PathVariable int lessonId) throws Exception {
        LecturerEntity lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        LessonEntity lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        List<QuestionVariantsDTO> list = questionVariantsService.getQuestionVariantsDTOList(lessonId);
        return ResponseEntity.ok(list);
    }
}
