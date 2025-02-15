package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.dtos.LecturerDTO;
import com.example.diplomaanahit.dtos.LessonDTO;
import com.example.diplomaanahit.dtos.QuestionAndVariantsForLecturerDTO;
import com.example.diplomaanahit.dtos.QuestionVariantsStudentDTO;
import com.example.diplomaanahit.dtos.SubjectDTO;
import com.example.diplomaanahit.entities.Lecturer;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.entities.Subject;
import com.example.diplomaanahit.mapper.LecturerMapper;
import com.example.diplomaanahit.mapper.SubjectMapper;
import com.example.diplomaanahit.services.LecturerService;
import com.example.diplomaanahit.services.LessonService;
import com.example.diplomaanahit.services.QuestionVariantsService;
import com.example.diplomaanahit.services.SubjectDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/lecturer{lecturerId}")
public class LecturerController {
    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private LecturerMapper lecturerMapper;

    @Autowired
    private SubjectDataService subjectDataService;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private QuestionVariantsService questionVariantsService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestionsOfAvailableLesson(@PathVariable Long lecturerId) throws Exception {
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        LecturerDTO lecturerDTO = lecturerMapper.toDTO(lecturer);
        return ResponseEntity.ok(lecturerDTO);
    }

    @RequestMapping(value = "subjects", method = RequestMethod.GET)
    public ResponseEntity<?> getSubjects(@PathVariable Long lecturerId) throws Exception {
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Set<Subject> subjects = lecturer.getLessons().stream().map(Lesson::getSubject).collect(Collectors.toSet());
        Set<SubjectDTO> subjectDTOS = subjectMapper.toDTOList(subjects);
        return ResponseEntity.ok(subjectDTOS);
    }

    @RequestMapping(value = "subjects/{subjectId}", method = RequestMethod.GET)
    public ResponseEntity<?> getLessonsOfSubject(@PathVariable Long lecturerId, @PathVariable Long subjectId) throws Exception {
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Subject subject = subjectDataService.findById(subjectId);
        if(subject == null){
            throw new Exception("Subject with this id is not exist");
        }
        List<Lesson> list = lessonService.findBySubject(subject);
        List<LessonDTO> lessonDTOList = lessonService.toDTOList(list);
        return ResponseEntity.ok(lessonDTOList);
    }

    @RequestMapping(value = "lesson/{lessonId}", method = RequestMethod.GET)
    public ResponseEntity<?> getLesson(@PathVariable Long lecturerId, @PathVariable Long lessonId) throws Exception {
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Lesson lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        LessonDTO lessonDTO = lessonService.toDTO(lesson);
        return ResponseEntity.ok(lessonDTO);
    }

    @RequestMapping(value = "lesson/{lessonId}/questions", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestions(@PathVariable Long lecturerId, @PathVariable Long lessonId) throws Exception {
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Lesson lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        List<QuestionVariantsEntity> list = questionVariantsService.findQuestionVariantsListByLessonId(lessonId);
        List<QuestionVariantsStudentDTO> dtoList = questionVariantsService.toDTOList(list);
        return ResponseEntity.ok(dtoList);
    }

    @RequestMapping(value = "lesson/{lessonId}/questions", method = RequestMethod.POST)
    public ResponseEntity<?> addQuestions(@PathVariable Long lecturerId, @PathVariable Long lessonId, List<QuestionAndVariantsForLecturerDTO> questions) throws Exception {
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Lesson lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        List<QuestionVariantsEntity> list = questionVariantsService.toEntityList(questions, lesson);
        questionVariantsService.saveAll(list);
        return ResponseEntity.ok(true);
    }
}
