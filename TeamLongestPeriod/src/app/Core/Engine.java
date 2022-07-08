package app.Core;

import app.Core.interfaces.Runnable;
import app.factories.RecordFactory;
import app.io.FileIO;
import app.io.Writer;
import app.model.Record;
import app.model.Team;
import app.services.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

import static app.constants.ApplicationConstants.*;

public class Engine implements Runnable {

    private FileIO fileIO;
    private Writer writer;
    private EmployeeService emplService;

    public Engine(FileIO fileIO, Writer writer, EmployeeService emplService) {
        this.fileIO = fileIO;
        this.writer = writer;
        this.emplService = emplService;
    }

    @Override
    public void run() {
        //Read all records data from .txt file
        List<Record> records = this.fileIO.read(FILE_PATH)
                .stream()
                .map(RecordFactory::execute)
                .collect(Collectors.toList());

        //Save all employee records into "database"
        this.emplService.addEmployeeRecords(records);

        //Find all team, couple of employees which r worked under same project and have overlap
        List<Team> teams = this.emplService.findAllTeamsWithOverlap();

        printResult(teams);
    }

    /**
     * If don't have couple of employees which are worked together under same project
     * will be print message with text "Doesn't exist teams", otherwise
     * will be find and print the team with best overlap under their joint projects.
     **/
    private void printResult(List<Team> teams) {
        if (teams.size() != EMPTY_COLLECTION_SIZE) {
            teams.sort((team1, team2) ->
                    (int) (team2.getTotalDuration() - team1.getTotalDuration()));
            Team bestTeam = teams.get(INDEX_ZERO);

            this.writer.write(
                    String.format(BEST_TEAM_PATTERN,
                            bestTeam.getFirstEmployeeId(),
                            bestTeam.getSecondEmployeeId(),
                            bestTeam.getTotalDuration()));
        } else {
            this.writer.write(NO_TEAMS_MSG);
        }
    }
}
