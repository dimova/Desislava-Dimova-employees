package app.services;

import app.model.Record;
import app.model.Team;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    void addEmployeeRecords(List<Record> records);

    List<Team> findAllTeamsWithOverlap();
    
    void recordProject(Record firstEmpl, Record secondEmpl, Long projectId, long overlapDays);

    void recordProjecstToFile()throws IOException;
}
