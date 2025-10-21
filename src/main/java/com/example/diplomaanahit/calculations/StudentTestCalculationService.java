package com.example.diplomaanahit.calculations;

import com.example.diplomaanahit.dtos.*;
import com.example.diplomaanahit.entities.*;
import com.example.diplomaanahit.services.*;
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

    @Autowired
    private ExamPointsService examPointsService;

    @Autowired
    private LessonDataService lessonService;

    public Grade submitAnswers(Student student, Lesson lesson, List<QuestionsAnswerDTO> questionsAnswerDTOS, Boolean isSatisfied) {
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
        grade.setSatisfied(isSatisfied);
        return grade;
    }

    public AssessmentType getAssessmentType(Integer correctAnswers, Integer total) {
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


    public Map<String, List<StudentDTO>> analyzeStudentGroupByMOG(StudentGroup studentGroup) {
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
            if(studentDTOS.stream().anyMatch(s -> s.getScore() != null && s.getScore() != 0.0)) {
                map.put("LessonName: " + l.getType() + " Lecturer: " + l.getLecturer().getName() + " " + l.getLecturer().getSurName() , studentDTOS);
            }
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

    public Map<String, Map<String, List<StudentDTO>>> analyzeStudentGroupsByDepartmentByMOG(List<StudentGroup> list) {
        Map<String, Map<String, List<StudentDTO>>> map = new HashMap<>();
        for (StudentGroup s : list) {
            map.put(s.getName(), analyzeStudentGroupByMOG(s));
        }
        return map;
    }

    public List<StudentGroupsAnalysisDTO> analyzeStudentGroups(List<StudentGroup> studentGroups, Lecturer lecturer, Subject subject, Lesson lesson) {
        if (subject != null){
            return analyzeStudentGroupsBySubject(studentGroups, lecturer,  subject);
        }
        else if (lesson != null){
            return analyzeStudentGroupsByLesson(studentGroups, lecturer, lesson.getType());
        }
        else {
            return analyzeStudentGroupsAll(studentGroups);
        }

    }

    private List<StudentGroupsAnalysisDTO> analyzeStudentGroupsAll(List<StudentGroup> studentGroups) {
        List<StudentGroupsAnalysisDTO> result = new ArrayList<>();
        for (StudentGroup studentGroup : studentGroups) {
            int countAttendance = 0;
            int countExcelentAnswearGrade = 0;
            int countGoodAnswearGrade = 0;
            int countSufficientAnswearGrade = 0;
            int countBadAnswearGrade = 0;
            int countSatisfiedTestPickers = 0;
            int countUnsatisfiedTestPickers = 0;

            for (Student student : studentGroup.getStudents()) {
                Set<Attendance> attendances = student.getAttendances();
                countAttendance += attendances.size();
                Set<Grade> grades = student.getGrades();
                for (Grade grade : grades) {
                    if ((double) grade.getScore() / grade.getMaxScore() >= 0.8) {
                        countExcelentAnswearGrade++;
                    } else if ((double) grade.getScore() / grade.getMaxScore() >= 0.6 && (double) grade.getScore() / grade.getMaxScore() < 0.8) {
                        countGoodAnswearGrade++;
                    } else if ((double) grade.getScore() / grade.getMaxScore() < 0.6 && (double) grade.getScore() / grade.getMaxScore() >= 0.4) {
                        countSufficientAnswearGrade++;
                    } else {
                        countBadAnswearGrade++;
                    }
                }
                if (!grades.isEmpty()) {
                    countSatisfiedTestPickers += (int) grades.stream().filter(grade -> grade.getIsSatisfied() != null && grade.getIsSatisfied() == 1).count();
                    countUnsatisfiedTestPickers += Math.max(grades.size() - countSatisfiedTestPickers, 0);
                }
            }
            if (studentGroup.getStudents().size() != 0) {
                countAttendance = countAttendance * 100 / studentGroup.getStudents().size();
                countExcelentAnswearGrade = countExcelentAnswearGrade * 100 / studentGroup.getStudents().size();
                countGoodAnswearGrade = countGoodAnswearGrade * 100 / studentGroup.getStudents().size();
                countSufficientAnswearGrade = countSufficientAnswearGrade * 100 / studentGroup.getStudents().size();
                countBadAnswearGrade = countBadAnswearGrade * 100 / studentGroup.getStudents().size();
                countSatisfiedTestPickers = countSatisfiedTestPickers * 100 / studentGroup.getStudents().size();
                countUnsatisfiedTestPickers = countUnsatisfiedTestPickers * 100 / studentGroup.getStudents().size();
            }
            StudentGroupsAnalysisDTO studentGroupAnalysisDTO = new StudentGroupsAnalysisDTO();
            studentGroupAnalysisDTO.setName(studentGroup.getName());
            studentGroupAnalysisDTO.setCountStudents(studentGroup.getStudents().size());
            studentGroupAnalysisDTO.setPercentAttendance(countAttendance);
            studentGroupAnalysisDTO.setPercentExcellentAnswears(countExcelentAnswearGrade);
            studentGroupAnalysisDTO.setPercentGoodAnswears(countGoodAnswearGrade);
            studentGroupAnalysisDTO.setPercentSufficienAnswears(countSufficientAnswearGrade);
            studentGroupAnalysisDTO.setPercentBadAnswears(countBadAnswearGrade);
            studentGroupAnalysisDTO.setPercentSatisfiedTestPickers(countSatisfiedTestPickers);
            studentGroupAnalysisDTO.setPercentUnsatisfiedTestPickers(countUnsatisfiedTestPickers);
            result.add(studentGroupAnalysisDTO);
        }

        return result;
    }

    private List<StudentGroupsAnalysisDTO> analyzeStudentGroupsByLesson(List<StudentGroup> studentGroups, Lecturer lecturer, String lesson) {
        List<StudentGroupsAnalysisDTO> result = new ArrayList<>();
        for(StudentGroup studentGroup : studentGroups)  {
            int countAttendance = 0;
            int countExcelentAnswearGrade = 0;
            int countGoodAnswearGrade = 0;
            int countSufficientAnswearGrade = 0;
            int countBadAnswearGrade = 0;
            int countSatisfiedTestPickers = 0;
            int countUnsatisfiedTestPickers = 0;

            for (Student student :studentGroup.getStudents()) {
                Set<Attendance> attendances = student.getAttendances().stream().filter(
                                attendance -> attendance.getLesson().getType().equals(lesson) &&
                                        attendance.getLesson().getLecturer().getId().equals(lecturer.getId())
                        && attendance.getLesson().getStudentGroup().getId().equals(studentGroup.getId()) && attendance.getIsPresent().equals(true))
                        .collect(Collectors.toSet());
                countAttendance += attendances.size();
                Set<Grade> grades = student.getGrades().stream().filter(
                                grade -> grade.getLesson().getType().equals(lesson))
                        .collect(Collectors.toSet());
                for (Grade grade : grades) {
                    if ((double) grade.getScore() / grade.getMaxScore() >= 0.8) {
                        countExcelentAnswearGrade++;
                    } else if ((double) grade.getScore() / grade.getMaxScore() >= 0.6 && (double) grade.getScore() / grade.getMaxScore() < 0.8) {
                        countGoodAnswearGrade++;
                    }else if ((double) grade.getScore() / grade.getMaxScore() < 0.6 && (double) grade.getScore() / grade.getMaxScore() >= 0.4) {
                        countSufficientAnswearGrade++;
                    } else {
                        countBadAnswearGrade++;
                    }
                }
                if (!grades.isEmpty()){
                    countSatisfiedTestPickers = (int) grades.stream().filter(grade -> grade.getLesson().getType().equals(lesson) && grade.getIsSatisfied() != null && grade.getIsSatisfied() == 1).count();
                    countUnsatisfiedTestPickers = Math.max(grades.size() - countSatisfiedTestPickers, 0);
                }
            }
            countAttendance = countAttendance*100/studentGroup.getStudents().size();
            countExcelentAnswearGrade = countExcelentAnswearGrade*100/studentGroup.getStudents().size();
            countGoodAnswearGrade = countGoodAnswearGrade*100/studentGroup.getStudents().size();
            countSufficientAnswearGrade = countSufficientAnswearGrade*100/studentGroup.getStudents().size();
            countBadAnswearGrade = countBadAnswearGrade*100/studentGroup.getStudents().size();
            countSatisfiedTestPickers = countSatisfiedTestPickers*100/studentGroup.getStudents().size();
            countUnsatisfiedTestPickers = countUnsatisfiedTestPickers*100/studentGroup.getStudents().size();
            StudentGroupsAnalysisDTO studentGroupAnalysisDTO = new StudentGroupsAnalysisDTO();
            studentGroupAnalysisDTO.setName(studentGroup.getName());
            studentGroupAnalysisDTO.setCountStudents(studentGroup.getStudents().size());
            studentGroupAnalysisDTO.setPercentAttendance(countAttendance);
            studentGroupAnalysisDTO.setPercentExcellentAnswears(countExcelentAnswearGrade);
            studentGroupAnalysisDTO.setPercentGoodAnswears(countGoodAnswearGrade);
            studentGroupAnalysisDTO.setPercentSufficienAnswears(countSufficientAnswearGrade);
            studentGroupAnalysisDTO.setPercentBadAnswears(countBadAnswearGrade);
            studentGroupAnalysisDTO.setPercentSatisfiedTestPickers(countSatisfiedTestPickers);
            studentGroupAnalysisDTO.setPercentUnsatisfiedTestPickers(countUnsatisfiedTestPickers);
            result.add(studentGroupAnalysisDTO);
        }
        return result;
    }

    private List<StudentGroupsAnalysisDTO> analyzeStudentGroupsBySubject(List<StudentGroup> studentGroups, Lecturer lecturer, Subject subject) {
        List<ExamPoints> list = examPointsService.findBySubjectAndLecturer(subject, lecturer);
        Map<StudentGroup, List<ExamPoints>> mapEntity = new HashMap<>();
        for (ExamPoints examPoints : list) {
            if (mapEntity.containsKey(examPoints.getStudent().getStudentGroup())) {
                mapEntity.get(examPoints.getStudent().getStudentGroup()).add(examPoints);
            } else {
                List<ExamPoints> examPointsList = new ArrayList<>();
                examPointsList.add(examPoints);
                mapEntity.put(examPoints.getStudent().getStudentGroup(), examPointsList);
            }
        }
        List<StudentGroupsAnalysisDTO> result = new ArrayList<>();
        for (Map.Entry<StudentGroup, List<ExamPoints>> entry : mapEntity.entrySet()) {
            int countLessons = lessonService.findBySubjectAndLecturer(subject, lecturer).size();
            int countExams = entry.getValue().size();
            int countAttendance = 0;
            int countExcelentAnswearGrade = 0;
            int countGoodAnswearGrade = 0;
            int countSufficientAnswearGrade = 0;
            int countBadAnswearGrade = 0;
            int countSatisfiedTestPickers = 0;
            int countUnsatisfiedTestPickers = 0;
            int countExcelentExamPoints = 0;
            int countGoodExamPoints = 0;
            int countSufficientExamPoints = 0;
            int countBadExamPoints = 0;
            for (Student student : entry.getKey().getStudents()) {
                Set<Attendance> attendances = student.getAttendances().stream().filter(
                        attendance -> attendance.getLesson().getSubject().getId().equals(subject.getId()))
                        .collect(Collectors.toSet());
                countAttendance += attendances.size();
                Set<Grade> grades = student.getGrades().stream().filter(
                        grade -> grade.getLesson().getSubject().getId().equals(subject.getId()))
                        .collect(Collectors.toSet());
                for (Grade grade : grades) {
                    if ((double) grade.getScore() / grade.getMaxScore() >= 0.8) {
                        countExcelentAnswearGrade++;
                    } else if ((double) grade.getScore() / grade.getMaxScore() >= 0.6 && (double) grade.getScore() / grade.getMaxScore() < 0.8) {
                        countGoodAnswearGrade++;
                    }else if ((double) grade.getScore() / grade.getMaxScore() < 0.6 && (double) grade.getScore() / grade.getMaxScore() >= 0.4) {
                        countSufficientAnswearGrade++;
                    } else {
                        countBadAnswearGrade++;
                    }
                }
                if (!grades.isEmpty()){
                    countSatisfiedTestPickers += (int) grades.stream().filter(grade -> grade.getIsSatisfied() != null && grade.getIsSatisfied() == 1).count();
                    countUnsatisfiedTestPickers += Math.max(grades.size() - countSatisfiedTestPickers, 0);
                }
                for (ExamPoints examPoints : list) {
                    if (examPoints.getStudent().getId().equals(student.getId())) {
                        if (examPoints.getPoint()/examPoints.getMaxPoint() >= 80) {
                            countExcelentExamPoints++;
                        } else if (examPoints.getPoint()/examPoints.getMaxPoint() >= 60 && examPoints.getPoint()/examPoints.getMaxPoint() < 80) {
                            countGoodExamPoints++;
                        } else if (examPoints.getPoint()/examPoints.getMaxPoint() < 60 && examPoints.getPoint()/examPoints.getMaxPoint() >= 40) {
                            countSufficientExamPoints++;
                        } else {
                            countBadExamPoints++;
                        }
                    }
                }
            }
            if (countExams != 0) {
                countExcelentExamPoints = countExcelentExamPoints * 100 / countExams;
                countGoodExamPoints = countGoodExamPoints * 100 / countExams;
                countSufficientExamPoints = countSufficientExamPoints * 100 / countExams;
                countBadExamPoints = countBadExamPoints * 100 / countExams;
            }
            if(countLessons != 0) {
                countAttendance = countAttendance * 100 / countLessons / entry.getKey().getStudents().size();
                countGoodAnswearGrade = countGoodAnswearGrade * 100 / entry.getKey().getStudents().size()/ countLessons;
                countSufficientAnswearGrade = countSufficientAnswearGrade * 100 / countLessons / entry.getKey().getStudents().size();
                countBadAnswearGrade = countBadAnswearGrade * 100 / countLessons/  entry.getKey().getStudents().size();
                countExcelentAnswearGrade = countExcelentAnswearGrade * 100 / countLessons/  entry.getKey().getStudents().size();
            }
            countAttendance /= entry.getKey().getStudents().size();
            StudentGroupsAnalysisDTO studentGroupAnalysisDTO = new StudentGroupsAnalysisDTO();
            studentGroupAnalysisDTO.setName(entry.getKey().getName());
            studentGroupAnalysisDTO.setCountLessons(countLessons);
            studentGroupAnalysisDTO.setCountStudents(entry.getKey().getStudents().size());
            studentGroupAnalysisDTO.setPercentAttendance(countAttendance);
            studentGroupAnalysisDTO.setPercentExcellentAnswears(countExcelentAnswearGrade);
            studentGroupAnalysisDTO.setPercentGoodAnswears(countGoodAnswearGrade);
            studentGroupAnalysisDTO.setPercentSufficienAnswears(countSufficientAnswearGrade);
            studentGroupAnalysisDTO.setPercentBadAnswears(countBadAnswearGrade);
            studentGroupAnalysisDTO.setPercentExcellentExamPoints(countExcelentExamPoints);
            studentGroupAnalysisDTO.setPercentGoodExamPoints(countGoodExamPoints);
            studentGroupAnalysisDTO.setPercentSufficientExamPoints(countSufficientExamPoints);
            studentGroupAnalysisDTO.setPercentBadExamPoints(countBadExamPoints);
            studentGroupAnalysisDTO.setPercentSatisfiedTestPickers(countSatisfiedTestPickers);
            studentGroupAnalysisDTO.setPercentUnsatisfiedTestPickers(countUnsatisfiedTestPickers);
            result.add(studentGroupAnalysisDTO);
        }
        return result;
    }


}
