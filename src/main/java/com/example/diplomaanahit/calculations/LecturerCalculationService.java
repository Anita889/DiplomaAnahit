package com.example.diplomaanahit.calculations;


import com.example.diplomaanahit.entities.Lecturer;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LecturerCalculationService {

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
}
