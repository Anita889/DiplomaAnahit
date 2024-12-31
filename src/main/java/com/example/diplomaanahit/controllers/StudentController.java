package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.entities.LessonEntity;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.entities.StudentEntity;
import com.example.diplomaanahit.services.LessonService;
import com.example.diplomaanahit.services.QuestionVariantsService;
import com.example.diplomaanahit.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/{studentId}")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private QuestionVariantsService questionVariantsService;

    @RequestMapping(value = "lesson/{lessonId}/listOfQuestions", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestionsOfAvailableLesson(@PathVariable int studentId,@PathVariable int lessonId) throws Exception {
        StudentEntity student = studentService.findById(studentId);
        if(student == null){
            throw new Exception("Student with this id is not exist");
        }
        LessonEntity lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        List<QuestionVariantsEntity> list = questionVariantsService.findQuestionVariantsListByLessonId(lessonId);
        return ResponseEntity.ok(list);
    }
}
