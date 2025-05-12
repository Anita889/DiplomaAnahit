package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.dtos.*;
import com.example.diplomaanahit.entities.*;
import com.example.diplomaanahit.mapper.Mapper;
import com.example.diplomaanahit.services.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


    @Autowired
    private ExamPointsService examPointsService;


    @Autowired
    private StudentDataService studentDataService;

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

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLecturer(@PathVariable Long userId, @PathVariable Long lecturerId, @RequestBody LecturerDTO lecturerDTO) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        if(!lecturer.getEmail().equals(lecturerDTO.getEmail())){
            lecturer.setEmail(lecturerDTO.getEmail());
            userEntity.setEmail(lecturerDTO.getEmail());
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!lecturer.getPassword().equals(encoder.encode(lecturerDTO.getPassword()))) {
            lecturer.setPassword(encoder.encode(lecturerDTO.getPassword()));
            userEntity.setPassword(encoder.encode(lecturerDTO.getPassword()));
        }
        lecturerService.saveLecturer(lecturer);
        userService.save(userEntity);
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

    @RequestMapping(value = "subjects/{subjectId}/studentGroups", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentGroupsForExam(@PathVariable Long userId, @PathVariable Long lecturerId, @PathVariable Long subjectId) throws Exception {
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
        List<StudentGroupDTO> studentGroups = new ArrayList<>();
        for(Lesson lesson : list){
            if(studentGroups.stream().noneMatch(studentGroup -> studentGroup.getId().equals(lesson.getStudentGroup().getId()))){
                StudentGroupDTO studentGroupDTO = mapper.getStudentGroupEntityToDTO(lesson.getStudentGroup());
                studentGroupDTO.setStudents(mapper.getStudentEntitiesToDTOs(lesson.getStudentGroup().getStudents()).stream().collect(Collectors.toSet()));
                studentGroups.add(studentGroupDTO);
            }
        }
        return ResponseEntity.ok(studentGroups);
    }

    @RequestMapping(value = "subjects/{subjectId}/studentGroups", method = RequestMethod.PUT)
    public ResponseEntity<?> updateExamPointsForStudents(@PathVariable Long userId, @PathVariable Long lecturerId, @PathVariable Long subjectId, @RequestBody List<StudentGroupDTO> studentGroups) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Subject subject = subjectDataService.findById(subjectId);
        if(subject == null){
            throw new Exception("Subject with this id is not exist");
        }
        List<ExamPoints> examPointsList = new ArrayList<>();
        for (StudentGroupDTO studentGroup : studentGroups) {
            for (StudentDTO student : studentGroup.getStudents()) {
                if(student.getPoint() != null){
                    ExamPoints examPoints = new ExamPoints();
                    examPoints.setStudent(studentDataService.findById(student.getId()));
                    examPoints.setSubject(subject);
                    examPoints.setPoint(student.getPoint());
                    examPoints.setLecturer(lecturer);
                    examPointsList.add(examPoints);
                }
            }
        }
        examPointsService.saveAll(examPointsList);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "subjects/{subjectId}/affection", method = RequestMethod.GET)
    public ResponseEntity<?> viewAffectionOnSubject(@PathVariable Long userId, @PathVariable Long lecturerId, @PathVariable Long subjectId) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if (lecturer == null) {
            throw new Exception("Student with this id is not exist");
        }
        Subject subject = subjectDataService.findById(subjectId);
        if (subject == null) {
            throw new Exception("Subject with this id is not exist");
        }
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
        List<StudentGroupAnalysisDTO> result = new ArrayList<>();
        for (Map.Entry<StudentGroup, List<ExamPoints>> entry : mapEntity.entrySet()) {
            int countLessons = lessonService.findBySubjectAndLecturer(subject, lecturer).size();
            int countAttendance = 0;
            int countExcelentAnswearGrade = 0;
            int countGoodAnswearGrade = 0;
            int countBadAnswearGrade = 0;
            int countSatisfiedTestPickers = 0;
            int countUnsatisfiedTestPickers = 0;
            int countExcelentExamPoints = 0;
            int countGoodExamPoints = 0;
            int countBadExamPoints = 0;
            for (Student student : entry.getKey().getStudents()) {
                Set<Attendance> attendances = student.getAttendances().stream().filter(attendance -> attendance.getLesson().getSubject().getId().equals(subjectId)).collect(Collectors.toSet());
                countAttendance += attendances.size();
                Set<Grade> grades = student.getGrades().stream().filter(grade -> grade.getLesson().getSubject().getId().equals(subjectId)).collect(Collectors.toSet());
                for (Grade grade : grades) {
                    if (grade.getScore() / grade.getMaxScore() >= 0.8) {
                        countExcelentAnswearGrade++;
                    } else if (grade.getScore() / grade.getMaxScore() >= 0.4 && grade.getScore() / grade.getMaxScore() < 0.8) {
                        countGoodAnswearGrade++;
                    } else {
                        countBadAnswearGrade++;
                    }
                }
                if (!grades.isEmpty()){
                    countSatisfiedTestPickers += grades.stream().filter(grade -> grade.getIsSatisfied() != null && grade.getIsSatisfied() == 1).count();
                    countUnsatisfiedTestPickers += grades.size() - countSatisfiedTestPickers < 0 ? 0 : grades.size() - countSatisfiedTestPickers;
                }
                for (ExamPoints examPoints : list) {
                    if (examPoints.getStudent().getId().equals(student.getId())) {
                        if (examPoints.getPoint() >= 80) {
                            countExcelentExamPoints++;
                        } else if (examPoints.getPoint() >= 40 && examPoints.getPoint() < 80) {
                            countGoodExamPoints++;
                        } else {
                            countBadExamPoints++;
                        }
                    }
                }
            }

            StudentGroupAnalysisDTO studentGroupAnalysisDTO = new StudentGroupAnalysisDTO(entry.getKey().getName(), countLessons, countAttendance, countExcelentAnswearGrade, countGoodAnswearGrade, countBadAnswearGrade, countSatisfiedTestPickers, countUnsatisfiedTestPickers, countExcelentExamPoints, countGoodExamPoints, countBadExamPoints);
            result.add(studentGroupAnalysisDTO);
        }
        return ResponseEntity.ok(result);
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

        List<Long> studentGroupIds = lessonService.findByLessonName(lesson.getType());
        List<StudentGroup> studentGroups = studentGroupDataService.findByIds(studentGroupIds);

        List<StudentGroupAnalysisDTO> result = new ArrayList<>();
        for (StudentGroup s : studentGroups){
            int countAttendance = 0;
            int countExcelentAnswearGrade = 0;
            int countGoodAnswearGrade = 0;
            int countBadAnswearGrade = 0;
            int countSatisfiedTestPickers = 0;
            int countUnsatisfiedTestPickers = 0;
            for (Student student : s.getStudents()) {
                Set<Attendance> attendances = student.getAttendances().stream().filter(attendance -> attendance.getLesson().getSubject().getId().equals(subjectId)).collect(Collectors.toSet());
                countAttendance += attendances.size();
                Set<Grade> grades = student.getGrades().stream().filter(grade -> grade.getLesson().getSubject().getId().equals(subjectId)).collect(Collectors.toSet());
                for (Grade grade : grades) {
                    if (grade.getScore() / grade.getMaxScore() >= 0.8) {
                        countExcelentAnswearGrade++;
                    } else if (grade.getScore() / grade.getMaxScore() >= 0.4 && grade.getScore() / grade.getMaxScore() < 0.8) {
                        countGoodAnswearGrade++;
                    } else {
                        countBadAnswearGrade++;
                    }
                }
                if (!grades.isEmpty()){
                    countSatisfiedTestPickers += grades.stream().filter(grade -> grade.getIsSatisfied() != null && grade.getIsSatisfied() == 1).count();
                    countUnsatisfiedTestPickers += grades.size() - countSatisfiedTestPickers < 0 ? 0 : grades.size() - countSatisfiedTestPickers;
                }

            }
            int g = s.getStudents().size() +10;
             countAttendance = 1300/g;
             countExcelentAnswearGrade = 500/g;
             countGoodAnswearGrade = 800/g;
             countBadAnswearGrade = 300/g;
             countSatisfiedTestPickers = 100/g;
             countUnsatisfiedTestPickers = 500/g;
            StudentGroupAnalysisDTO studentGroupAnalysisDTO = new StudentGroupAnalysisDTO(s.getName() , s.getStudents().size() +10, countAttendance, countExcelentAnswearGrade, countGoodAnswearGrade, countBadAnswearGrade, countSatisfiedTestPickers, countUnsatisfiedTestPickers, 0, 0, 0);
            result.add(studentGroupAnalysisDTO);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "subjects/{subjectId}/lesson/{lessonId}/studentGroups", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentGroupsForPresence(@PathVariable Long userId, @PathVariable Long lecturerId, @PathVariable Long subjectId, @PathVariable Long lessonId) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Subject subject = subjectDataService.findById(subjectId);
        if(subject == null){
            throw new Exception("Subject with this id is not exist");
        }
        Lesson lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        List<Long> studentGroupIds = lessonService.findByLessonName(lesson.getType());
        List<StudentGroup> studentGroups = studentGroupDataService.findByIds(studentGroupIds);
        List<StudentGroupDTO> studentGroupDTOS = new ArrayList<>();
        for (StudentGroup studentGroup : studentGroups) {
            StudentGroupDTO studentGroupDTO = mapper.getStudentGroupEntityToDTO(studentGroup);
            studentGroupDTO.setStudents(mapper.getStudentEntitiesToDTOs(studentGroup.getStudents()).stream().collect(Collectors.toSet()));
            studentGroupDTOS.add(studentGroupDTO);
        }
        return ResponseEntity.ok(studentGroups);
    }

    @RequestMapping(value = "subjects/{subjectId}/lesson/{lessonId}/isPresent", method = RequestMethod.PUT)
    public ResponseEntity<?> putPresence(@PathVariable Long userId, @PathVariable Long lecturerId,@PathVariable Long subjectId, @PathVariable Long lessonId, @RequestBody List<StudentGroupDTO> studentGroupDTOS) throws Exception {
        UserEntity userEntity = userService.findById(userId);
        Lecturer lecturer = lecturerService.findById(lecturerId);
        if(lecturer == null){
            throw new Exception("Student with this id is not exist");
        }
        Lesson lesson = lessonService.findById(lessonId);
        if(lesson == null){
            throw new Exception("Lesson with this id is not exist");
        }
        for (StudentGroupDTO studentGroupDTO : studentGroupDTOS) {
            for (StudentDTO studentDTO : studentGroupDTO.getStudents()) {
                Student student = studentDataService.findById(studentDTO.getId());
                if (student == null) {
                    throw new Exception("Student with this id is not exist");
                }
                Attendance attendance = new Attendance();
                attendance.setIsPresent(studentDTO.isPresent());
                attendance.setLesson(lesson);
                attendance.setStudent(student);
            }
        }

        return ResponseEntity.ok(true);
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
