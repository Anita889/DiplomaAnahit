package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.calculations.StudentTestCalculationService;
import com.example.diplomaanahit.dtos.GradeDTO;
import com.example.diplomaanahit.dtos.LessonDTO;
import com.example.diplomaanahit.dtos.QuestionVariantsStudentDTO;
import com.example.diplomaanahit.dtos.QuestionsAnswerDTO;
import com.example.diplomaanahit.dtos.StudentDTO;
import com.example.diplomaanahit.dtos.SubjectDTO;
import com.example.diplomaanahit.entities.Grade;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.entities.Student;
import com.example.diplomaanahit.entities.Subject;
import com.example.diplomaanahit.mapper.Mapper;
import com.example.diplomaanahit.services.GradeDataService;
import com.example.diplomaanahit.services.LessonDataService;
import com.example.diplomaanahit.services.QuestionVariantsDataService;
import com.example.diplomaanahit.services.StudentDataService;
import com.example.diplomaanahit.services.SubjectDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(value = "api/user/{userId}/student/{studentId}")
public class StudentController {
    @Autowired
    private StudentTestCalculationService studentCalculationService;

    @Autowired
    private LessonDataService lessonService;

    @Autowired
    private QuestionVariantsDataService questionVariantsService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private SubjectDataService subjectDataService;

    @Autowired
    private StudentDataService studentService;

    @Autowired
    private GradeDataService gradeService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getStudent(@PathVariable Long userId, @PathVariable Long studentId) throws Exception {
        Student student = studentCalculationService.findById(studentId);
        if(student == null){
            throw new Exception("Student with this id is not exist");
        }
        StudentDTO studentDTO = mapper.getLecturerEntityToDTO(student);
        return ResponseEntity.ok(studentDTO);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long userId, @PathVariable Long studentId, @RequestBody StudentDTO studentDTO) throws Exception {
        Student student = studentCalculationService.findById(studentId);
        if(student == null){
            throw new Exception("Student with this id is not exist");
        }
        student.setStudentGroup(mapper.getStudentGroupDTOToEntity(studentDTO.getStudentGroupDTO()));
        studentService.save(student);
        return ResponseEntity.ok(studentDTO);
    }

    @RequestMapping(value = "subjects", method = RequestMethod.GET)
    public ResponseEntity<?> getSubjects(@PathVariable Long userId, @PathVariable Long studentId) throws Exception {
        Student student = studentCalculationService.findById(studentId);
        if(student == null){
            throw new Exception("Student with this id is not exist");
        }
        List<Subject> list = subjectDataService.findAllSubjectsByStudentGroup(student.getStudentGroup());
        List<SubjectDTO> subjectDTOList = subjectDataService.toDTOList(list);
        return ResponseEntity.ok(subjectDTOList);
    }

    @RequestMapping(value = "subjects/{subjectId}", method = RequestMethod.GET)
    public ResponseEntity<?> getLessonsOfSubject(@PathVariable Long userId, @PathVariable Long studentId, @PathVariable Long subjectId) throws Exception {
        Student student = studentCalculationService.findById(studentId);
        if(student == null){
            throw new Exception("Student with this id is not exist");
        }
        Subject subject = subjectDataService.findById(subjectId);
        if(subject == null){
            throw new Exception("Subject with this id is not exist");
        }
        List<Lesson> list = lessonService.findAllByStudentGroup(subject);
        List<LessonDTO> lessonDTOList = lessonService.toDTOList(list);
        return ResponseEntity.ok(lessonDTOList);
    }

    @RequestMapping(value = "lesson/{lessonId}", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestionsAndVariants(@PathVariable Long userId, @PathVariable Long studentId, @PathVariable Long lessonId) throws Exception {
        Student student = studentCalculationService.findById(studentId);
        if(student == null){
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

    @RequestMapping(value = "lesson/{lessonId}", method = RequestMethod.POST)
    public ResponseEntity<?> submitAnswers(@PathVariable Long userId, @PathVariable Long studentId, @PathVariable Long lessonId,@RequestBody List<QuestionsAnswerDTO> questionsAnswerDTOS) throws Exception {
        Student student = studentCalculationService.findById(studentId);
        if(student == null){
            throw new Exception("Student with this id is not exist");
        }
        Lesson lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        if (lesson.getAvailableDate().isAfter(LocalDate.now())){
            throw new Exception("Lesson is not available yet");
        }
        Grade grade = studentCalculationService.submitAnswers(student, lesson, questionsAnswerDTOS);
        gradeService.save(grade);
        GradeDTO gradeDTO = new GradeDTO();
        gradeDTO.setDescription(grade.getAssessmentType().getName());
        gradeDTO.setGrade(grade.getScore() + "/" +questionsAnswerDTOS.size());
        return ResponseEntity.ok(gradeDTO);
    }
}
