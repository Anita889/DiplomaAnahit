package com.example.diplomaanahit.calculations;


import com.example.diplomaanahit.dtos.LecturersAnalysisDTO;
import com.example.diplomaanahit.dtos.SubjectQualityDTO;
import com.example.diplomaanahit.entities.*;
import com.example.diplomaanahit.repositories.ExamPointsRepository;
import com.example.diplomaanahit.services.ExamPointsService;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecturerCalculationService {

    @Autowired
    private ExamPointsService examPointsService;

    public Double calculateLecturers(Set<Lecturer> lecturers) {
        Double factor = 0.0;
        for (Lecturer lecturer : lecturers) {
            if(lecturer.getRating().equals("professor")) {
                factor += 1;
            }
            else if(lecturer.getRating().equals("associate professor")) {
                factor += 0.75;
            }
            else if(lecturer.getRating().equals("assistant professor")) {
                factor += 0.5;
            }
            else if(lecturer.getRating().equals("lecturer")) {
                factor += 0.25;
            }
        }
        return factor / lecturers.size();
    }

    public List<LecturersAnalysisDTO> analyzeLecturers(List<Lecturer> lecturers) {
        List<LecturersAnalysisDTO> analysis = new ArrayList<>();
        for (Lecturer lecturer : lecturers) {
            LecturersAnalysisDTO dto = new LecturersAnalysisDTO();
            dto.setName(lecturer.getName());
            dto.setSurName(lecturer.getSurName());
            dto.setCountLessons(lecturer.getLessons().size());
            dto.setSubjectQuality(getSubjectQualityForLecturer(lecturer));
            analysis.add(dto);
        }
        return analysis;
    }

    private List<SubjectQualityDTO> getSubjectQualityForLecturer(Lecturer lecturer) {
        List<SubjectQualityDTO> subjectQualityList = new ArrayList<>();
        List<Subject> subjectsNames = lecturer.getLessons().stream()
                .map(Lesson::getSubject)
                .toList();
        for (Subject subject : subjectsNames) {
            SubjectQualityDTO subjectQuality = new SubjectQualityDTO();
            subjectQuality.setSubjectName(subject.getName());
            subjectQuality.setCountLessons(lecturer.getLessons().stream()
                    .filter(lesson -> lesson.getSubject().getId().equals(subject.getId()))
                    .count());
            updateSubjectQuality(subjectQuality, subject, lecturer);
            subjectQualityList.add(subjectQuality);
        }
        return subjectQualityList;
    }

    private void updateSubjectQuality(SubjectQualityDTO subjectQuality, Subject subject, Lecturer lecturer) {
        List<ExamPoints> list = examPointsService.findBySubjectAndLecturer(subject, lecturer);
        List<Lesson> lessons = lecturer.getLessons().stream()
                .filter(lesson -> lesson.getSubject().getId().equals(subject.getId()))
                .collect(Collectors.toList());
        int countStudents = 0;
        int countLessons = lessons.size();
        int countExams = list.stream()
                .map(examPoints -> examPoints.getStudent().getId())
                .collect(Collectors.toSet())
                .size();
        if (countExams != 0) {
            countExams/=list.size()/countExams;
        }
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
        for (Lesson lesson : lessons) {
            for (Student student : lesson.getStudentGroup().getStudents()) {
                countStudents++;
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
        }
        if (countExams != 0) {
            countExcelentExamPoints = countExcelentExamPoints * 100 / countExams;
            countGoodExamPoints = countGoodExamPoints * 100 / countExams;
            countSufficientExamPoints = countSufficientExamPoints * 100 / countExams;
            countBadExamPoints = countBadExamPoints * 100 / countExams;
        }
        if(countLessons != 0) {
            countAttendance = countAttendance * 100 / countLessons / countStudents;
            countGoodAnswearGrade = countGoodAnswearGrade * 100 / countStudents;
            countSufficientAnswearGrade = countSufficientAnswearGrade * 100 / countLessons / countStudents;
            countBadAnswearGrade = countBadAnswearGrade * 100 / countLessons/  countStudents;
            countExcelentAnswearGrade = countExcelentAnswearGrade * 100 / countLessons/  countStudents;
        }

        subjectQuality.setPercentBadAnswears(countExcelentAnswearGrade);
        subjectQuality.setPercentExcellentAnswears(countGoodAnswearGrade);
        subjectQuality.setPercentGoodAnswears(countSufficientAnswearGrade);
        subjectQuality.setPercentSufficienAnswears(countBadAnswearGrade);
        subjectQuality.setPercentAttendance(countAttendance);
        subjectQuality.setPercentExcellentExamPoints(countExcelentExamPoints);
        subjectQuality.setPercentGoodExamPoints(countGoodExamPoints);
        subjectQuality.setPercentSufficientExamPoints(countSufficientExamPoints);
        subjectQuality.setPercentBadExamPoints(countBadExamPoints);
        subjectQuality.setPercentSatisfiedTestPickers(countSatisfiedTestPickers);
        subjectQuality.setPercentUnsatisfiedTestPickers(countUnsatisfiedTestPickers);
        subjectQuality.setCountLessons(countLessons);
        subjectQuality.setCountStudents(countStudents);
        subjectQuality.setCountExams(countExams);
        subjectQuality.setCountAttendance(countAttendance);

    }
}
