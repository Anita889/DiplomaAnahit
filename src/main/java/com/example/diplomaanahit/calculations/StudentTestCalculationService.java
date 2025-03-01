package com.example.diplomaanahit.calculations;

import com.example.diplomaanahit.dtos.QuestionsAnswerDTO;
import com.example.diplomaanahit.entities.AssessmentType;
import com.example.diplomaanahit.entities.Attendance;
import com.example.diplomaanahit.entities.Grade;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.entities.Student;
import com.example.diplomaanahit.entities.StudentGroup;
import com.example.diplomaanahit.services.AssessmentDataService;
import com.example.diplomaanahit.services.QuestionVariantsDataService;
import com.example.diplomaanahit.services.StudentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class StudentTestCalculationService {


    @Autowired
    private QuestionVariantsDataService questionVariantsService;

    @Autowired
    private StudentDataService studentService;

    @Autowired
    private AssessmentDataService assessmentService;

    public Grade submitAnswers(Student student, Lesson lesson, List<QuestionsAnswerDTO> questionsAnswerDTOS) {
        List<QuestionVariantsEntity> questionVariantsEntities = questionVariantsService.findQuestionVariantsListByLessonId(lesson.getId());
        Integer correctAnswers = 0;
        Integer total = 0;
        for (QuestionsAnswerDTO dto : questionsAnswerDTOS) {
            total++;
            for (QuestionVariantsEntity entity : questionVariantsEntities) {
                if (dto.getQuestion().equals(entity.getQuestion())) {
                    if (dto.getFirstVariant() && entity.getNumber() == 1) {
                        correctAnswers++;
                    } else if (dto.getSecondVariant() && entity.getNumber() == 2) {
                        correctAnswers++;
                    } else if (dto.getThirdVariant() && entity.getNumber() == 3) {
                        correctAnswers++;
                    }
                }
            }
        }
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setScore(correctAnswers);
        grade.setMaxScore(total);
        grade.setAssessmentType(getAssessmentType(correctAnswers, total));
        if(student.getGrades() == null){
            student.setGrades(Set.of(grade));
        }
        else{
            student.getGrades().add(grade);
        }
        return grade;
    }

    private AssessmentType getAssessmentType(Integer correctAnswers, Integer total) {
        AssessmentType assessmentType;
        if(correctAnswers < 0.4 * total){
         assessmentType = assessmentService.findByAssessmentType("INSUFFICIENT");
        }
        else if(correctAnswers < 0.6 * total){
            assessmentType = assessmentService.findByAssessmentType("SUFFICIENT");
        }
        else if(correctAnswers < 0.8  * total){
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

    public Double calculateStudentGroup(StudentGroup studentGroup) {
        Double factor = 0.0;
        for (Student student : studentGroup.getStudents()) {
            Set<Attendance> attendances = student.getAttendances();
            int present = attendances.stream().map(Attendance::getIsPresent).toList().size();
            double presentFactor = (double) present /attendances.size();
            Set<Grade> grades = student.getGrades();
            double totalScoreFactor = 0;
            for (Grade grade : grades) {
                totalScoreFactor += (double) grade.getScore() /grade.getMaxScore();
            }
            totalScoreFactor /= grades.size();
            factor += totalScoreFactor * presentFactor;
        }
        return factor / studentGroup.getStudents().size() * 100;
    }
}
