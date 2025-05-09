package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.dtos.LecturerDTO;
import com.example.diplomaanahit.dtos.LessonDTO;
import com.example.diplomaanahit.dtos.LessonSimpleDTO;
import com.example.diplomaanahit.dtos.QuestionVariantsLecturerDTO;
import com.example.diplomaanahit.dtos.StudentDTO;
import com.example.diplomaanahit.dtos.StudentGroupDTO;
import com.example.diplomaanahit.dtos.SubjectDTO;
import com.example.diplomaanahit.entities.Grade;
import com.example.diplomaanahit.entities.Lecturer;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.entities.Student;
import com.example.diplomaanahit.entities.StudentGroup;
import com.example.diplomaanahit.entities.Subject;
import com.example.diplomaanahit.entities.UserEntity;
import com.example.diplomaanahit.mapper.Mapper;
import com.example.diplomaanahit.services.LecturerDataService;
import com.example.diplomaanahit.services.LessonDataService;
import com.example.diplomaanahit.services.QuestionVariantsDataService;
import com.example.diplomaanahit.services.StudentGroupDataService;
import com.example.diplomaanahit.services.SubjectDataService;
import com.example.diplomaanahit.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/user/{userId}/lecturers/{lecturerId}")
public class LecturerController {
    @Autowired
    private LecturerDataService lecturerService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private SubjectDataService subjectDataService;

    @Autowired
    private LessonDataService lessonService;

    @Autowired
    private QuestionVariantsDataService questionVariantsService;

    @Autowired
    private UserDataService userService;

    @Autowired
    private StudentGroupDataService studentGroupDataService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getLecturer(@PathVariable Long userId, @PathVariable Long lecturerId) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        LecturerDTO lecturerDTO = mapper.getStudentEntityToDTO(lecturer);
        return ResponseEntity.ok(lecturerDTO);
    }

    @RequestMapping(value = "subjects", method = RequestMethod.GET)
    public ResponseEntity<?> getSubjects(@PathVariable Long userId, @PathVariable Long lecturerId) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Lecturer with this id is not exist");
        }
        Set<Subject> subjects = lecturer.getLessons().stream().map(Lesson::getSubject).collect(Collectors.toSet());
        Set<SubjectDTO> subjectDTOS = mapper.getSubjectToDTOs(subjects);
        return ResponseEntity.ok(subjectDTOS);
    }

    @RequestMapping(value = "subjects/{subjectId}", method = RequestMethod.GET)
    public ResponseEntity<?> getLessonsOfSubject(@PathVariable Long userId, @PathVariable Long lecturerId, @PathVariable Long subjectId) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Subject subject = subjectDataService.findById(subjectId);
        if(subject == null){
            throw new Exception("Subject with this id is not exist");
        }
        List<Lesson> list = lessonService.findBySubject(subject);
        List<LessonSimpleDTO> lessonDTOList = lessonService.toSimpleDTOList(list);
        return ResponseEntity.ok(lessonDTOList);
    }

    @RequestMapping(value = "question/{questionId}", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestion(@PathVariable Long userId, @PathVariable Long lecturerId, @PathVariable Long questionId) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        QuestionVariantsEntity questionVariantsEntity = questionVariantsService.findById(questionId);
        if(questionVariantsEntity == null){
            throw new Exception("QuestionVariantsEntity with this id is not exist");
        }
        QuestionVariantsLecturerDTO lessonDTO = questionVariantsService.toDTO(questionVariantsEntity);
        return ResponseEntity.ok(lessonDTO);
    }

    @RequestMapping(value = "lesson/{lessonId}", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestions(@PathVariable Long userId, @PathVariable Long lecturerId, @PathVariable Long lessonId) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Lesson lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        List<QuestionVariantsEntity> list = questionVariantsService.findQuestionVariantsListByLessonName(lesson.getType());
        List<QuestionVariantsLecturerDTO> dtoList = questionVariantsService.toQuestionVariantsLecturerDTOList(list);
        return ResponseEntity.ok(dtoList);
    }

    @RequestMapping(value = "subjects/{subjectId}/lesson/{lessonId}/affection", method = RequestMethod.GET)
    public ResponseEntity<?> getAffection(@PathVariable Long userId, @PathVariable Long lecturerId,@PathVariable Long subjectId, @PathVariable Long lessonId) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Lesson lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        String lessonName = lesson.getType();
        List<Long> studentGroupIds = lessonService.findByLessonName(lesson.getType());
        List<StudentGroup> studentGroups = studentGroupDataService.findByIds(studentGroupIds);
        Map<String, List<StudentDTO>> dtoMap = new HashMap<>();
        for (StudentGroup s : studentGroups){
            StudentGroupDTO studentGroupDTO = mapper.getStudentGroupEntityToDTO(s);
            List<StudentDTO> studentDTOS = mapper.getStudentEntitiesToDTOs(s.getStudents());
            for(Student student : s.getStudents()){
                Set<Grade> grades = student.getGrades().stream().filter(grade -> grade.getLesson().getType().contains(lessonName)).collect(Collectors.toSet());
                Double score = grades.stream().mapToDouble(Grade::getScore).sum();
                Double maxScore = grades.stream().mapToDouble(Grade::getMaxScore).sum();
                StudentDTO studentDTO = studentDTOS.stream().filter(studentDTO1 -> studentDTO1.getId().equals(student.getId())).findFirst().get();
                if (!grades.isEmpty()) {
                    studentDTO.setScore(score/maxScore * 100);
                }
            }
            if(studentDTOS.stream().anyMatch(studentDTO -> studentDTO.getScore() != null)){
                dtoMap.put(studentGroupDTO.getName(), studentDTOS);
            }
        }
        return ResponseEntity.ok(dtoMap);
    }

    @RequestMapping(value = "lesson/{lessonId}/questions/add", method = RequestMethod.POST)
    public ResponseEntity<?> addQuestions(@PathVariable Long userId, @PathVariable Long lecturerId, @PathVariable Long lessonId, @RequestBody QuestionVariantsLecturerDTO question) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Lesson lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        List<QuestionVariantsEntity> list = questionVariantsService.toEntityList(Collections.singletonList(question), lesson);
        questionVariantsService.saveAll(list);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "questions/{questionId}/remove", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteQuestion(@PathVariable Long userId, @PathVariable Long lecturerId, @PathVariable Long questionId) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        QuestionVariantsEntity questionVariantsEntity = questionVariantsService.findById(questionId);
        if(questionVariantsEntity == null){
            throw new Exception("Question with this id is not exist");
        }
        questionVariantsService.delete(questionId);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "questions/{questionId}/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateQuestion(@PathVariable Long userId, @PathVariable Long lecturerId,@PathVariable Long questionId, @RequestBody QuestionVariantsLecturerDTO question) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        QuestionVariantsEntity questionVariantsEntity = questionVariantsService.findById(questionId);
        if(questionVariantsEntity == null){
            throw new Exception("Question with this id is not exist");
        }
        questionVariantsEntity.setQuestion(question.getQuestion());
        questionVariantsEntity.setVariant1(question.getVariant1());
        questionVariantsEntity.setVariant2(question.getVariant2());
        questionVariantsEntity.setVariant3(question.getVariant3());
        if(question.getCorrectAnswer().equals(question.getVariant1())){
           questionVariantsEntity.setNumber(1);
        }
        else if(question.getCorrectAnswer().equals(question.getVariant2())){
            questionVariantsEntity.setNumber(2);
        }
        else if(question.getCorrectAnswer().equals(question.getVariant3())){
            questionVariantsEntity.setNumber(3);
        }
        else {
            throw new Exception("Correct variant is not exist");
        }
        questionVariantsService.save(questionVariantsEntity);
        return ResponseEntity.ok(true);
    }
}
