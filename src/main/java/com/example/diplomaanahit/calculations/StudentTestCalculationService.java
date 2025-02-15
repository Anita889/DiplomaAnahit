package com.example.diplomaanahit.calculations;

import com.example.diplomaanahit.dtos.QuestionsAnswerDTO;
import com.example.diplomaanahit.entities.AssessmentType;
import com.example.diplomaanahit.entities.Grade;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.entities.Student;
import com.example.diplomaanahit.services.AssessmentService;
import com.example.diplomaanahit.services.QuestionVariantsService;
import com.example.diplomaanahit.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class StudentTestCalculationService {


    @Autowired
    private QuestionVariantsService questionVariantsService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AssessmentService assessmentService;

    public Grade submitAnswers(Student student, Lesson lesson, List<QuestionsAnswerDTO> questionsAnswerDTOS) {
        List<QuestionVariantsEntity> questionVariantsEntities = questionVariantsService.findQuestionVariantsListByLessonId(lesson.getId());
        Integer correctAnswers = 0;
        for (QuestionsAnswerDTO dto : questionsAnswerDTOS) {
            for (QuestionVariantsEntity entity : questionVariantsEntities) {
                if (dto.getQuestion().equals(entity.getQuestion())) {
                    if (dto.getFirstVariant() && entity.getNumber() == 1) {
                        correctAnswers++;
                    } else if (dto.getSecondVariant() && entity.getNumber() == 2) {
                        correctAnswers++;
                    } else if (dto.getThirdVariant() && entity.getNumber() == 3) {
                        correctAnswers++;
                    } else {
                        throw new RuntimeException("Invalid variant");
                    }
                }
            }
        }
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setScore(correctAnswers);
        grade.setAssessmentType(getAssessmentType(correctAnswers));
        if(student.getGrades() == null){
            student.setGrades(Set.of(grade));
        }
        else{
            student.getGrades().add(grade);
        }
        return grade;
    }

    private AssessmentType getAssessmentType(Integer correctAnswers) {
        AssessmentType assessmentType;
        if(correctAnswers < 8){
         assessmentType = assessmentService.findByAssessmentType("INSUFFICIENT");
        }
        else if(correctAnswers < 12){
            assessmentType = assessmentService.findByAssessmentType("SUFFICIENT");
        }
        else if(correctAnswers < 16){
            assessmentType = assessmentService.findByAssessmentType("GOOD");
        }
        else{
            assessmentType = assessmentService.findByAssessmentType("EXCELLENT");
        }
        return assessmentType;
    }

    public Student findById(Long studentId) {
        return studentService.findById(studentId);
    }
}
