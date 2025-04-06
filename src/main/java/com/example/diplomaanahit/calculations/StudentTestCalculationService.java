package com.example.diplomaanahit.calculations;

import com.example.diplomaanahit.dtos.QuestionsAnswerDTO;
import com.example.diplomaanahit.dtos.StudentDTO;
import com.example.diplomaanahit.dtos.StudentGroupDTO;
import com.example.diplomaanahit.entities.AssessmentType;
import com.example.diplomaanahit.entities.Attendance;
import com.example.diplomaanahit.entities.Department;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        grade.setLesson(lesson);
        grade.setStudent(student);
        grade.setScore(correctAnswers);
        grade.setMaxScore(total);
        grade.setAssessmentType(getAssessmentType(correctAnswers, total));
        if (student.getGrades() == null) {
            student.setGrades(Set.of(grade));
        } else {
            student.getGrades().add(grade);
        }
        return grade;
    }

    private AssessmentType getAssessmentType(Integer correctAnswers, Integer total) {
        AssessmentType assessmentType;
        if (correctAnswers < 0.4 * total) {
            assessmentType = assessmentService.findByAssessmentType("INSUFFICIENT");
        } else if (correctAnswers < 0.6 * total) {
            assessmentType = assessmentService.findByAssessmentType("SUFFICIENT");
        } else if (correctAnswers < 0.8 * total) {
            assessmentType = assessmentService.findByAssessmentType("GOOD");
        } else {
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
            attendances.stream().filter(Attendance::getIsPresent).collect(Collectors.toSet());
            Set<Grade> grades = student.getGrades();
            for (Attendance a : attendances) {
                grades.stream().filter(g -> g.getLesson().getId().equals(a.getLesson().getId()));
            }

            for (Grade g : grades) {
                factor += (double) g.getScore() / g.getMaxScore();
            }
//            factor += totalScoreFactor * presentFactor;
        }
        return factor / studentGroup.getStudents().size();
    }

    public Double calculateDepartment(List<StudentGroup> studentGroups) {
        Double factor = 0.0;
        for (StudentGroup s : studentGroups) {
            factor += calculateStudentGroup(s);
        }
        return factor / studentGroups.size();
    }

    public Map<String, Double> calculateDepartmentAndShow(List<StudentGroup> studentGroups) {
        Map<String, Double> f = new HashMap<>();
        for (StudentGroup s : studentGroups) {
            f.put(s.getName(), calculateStudentGroup(s));
        }
        return f;
    }

    public Map<String, List<StudentDTO>> analyzeStudentGroup(StudentGroup studentGroup) {
        Map<String, List<StudentDTO>> map = new HashMap<>();
        Set<Student> students = studentGroup.getStudents();
        for (Lesson l : studentGroup.getLessons()) {
            List<StudentDTO> studentDTOS = new ArrayList<>();
            for (Student s : students) {
                StudentDTO studentDTO = new StudentDTO();
                studentDTO.setId(s.getId());
                studentDTO.setStudentName(s.getName());
                studentDTO.setStudentSurname(s.getSurname());
                studentDTO.setMog(s.getMog());
                studentDTO.setScore(calculateStudentMiddleScore(s, l));
                studentDTOS.add(studentDTO);
            }
            map.put(l.getType() , studentDTOS);
        }
        return map;
    }

    private Double calculateStudentMiddleScore(Student s, Lesson l) {
        Set<Grade> grades = s.getGrades();
        Double factor = 0.0;
        for (Grade g : grades) {
            if (g.getLesson().getId().equals(l.getId()) && s.getAttendances().stream().anyMatch(a -> a.getLesson().getId().equals(l.getId()))) {
                factor += (double) g.getScore() / g.getMaxScore();
            }
        }
        if (grades.isEmpty()) {
            return 0.0;
        }
        return 100 * factor / grades.size();
    }

    public Map<String, Map<String, List<StudentDTO>>> analyzeStudentGroupsByDepartment(List<StudentGroup> list) {
        Map<String, Map<String, List<StudentDTO>>> map = new HashMap<>();
        for (StudentGroup s : list) {
            map.put(s.getName(), analyzeStudentGroup(s));
        }
        return map;
    }
}
