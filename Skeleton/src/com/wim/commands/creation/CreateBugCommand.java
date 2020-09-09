package com.wim.commands.creation;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementFactory;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.BugStatusType;
import com.wim.models.common.enums.PriorityType;
import com.wim.models.common.enums.SeverityType;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Bug;
import com.wim.models.contracts.Member;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.wim.commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;

public class CreateBugCommand extends CommandImpl implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private final ItemManagementFactory factory;
    private final ItemManagementRepository repository;

    private String title;
    private String description;
    private PriorityType priority;
    private SeverityType severity;
    private List<String> stepsToReproduce;
    private Board board;
    private String teamName;

    public CreateBugCommand(ItemManagementFactory factory, ItemManagementRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<String> paramsWithoutDescription = removeDescriptionFromList(parameters);
        List<String> paramsWithoutSteps = removeStepsFromList(paramsWithoutDescription);
        validateInput(paramsWithoutSteps,EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        addBugToBoard(title, description, priority, severity, stepsToReproduce, board, teamName);
        addToBoardHistory(title, board);
        return executionMessage(title, board);
    }

    private void parseParameters(List<String> parameters) {

        try {
            stepsToReproduce = convertStepsToList(parameters);
            parameters = removeStepsFromList(parameters);
        } catch (Exception e) {
            throw new IllegalArgumentException("Please enter stepsToReproduce in curly brackets {...}");
        }

        try {
            description = separateDescriptionFromList(parameters);
        } catch (Exception e) {
            throw new IllegalArgumentException("Please enter description in brackets [...]");
        }
        List<String> newParameters = removeDescriptionFromList(parameters);
        try {
            title = newParameters.get(0);
            priority = PriorityType.valueOf(newParameters.get(1).toUpperCase());
            severity = SeverityType.valueOf(newParameters.get(2).toUpperCase());
            teamName = newParameters.get(3);
            board = repository.findBoard(newParameters.get(4), teamName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse CreateBug command parameters.");
        }
    }

    private List<String> convertStepsToList(List<String> parameters) {
        String fullCommand = parameters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        int beginIndex = fullCommand.indexOf("{");
        int endIndex = fullCommand.indexOf("}");
        // {first step is this | second step is this| and last one }   this is how we have to enter stepsToReproduce
        String newCommand = fullCommand.substring(beginIndex+1, endIndex);
        List<String> newParameters = Arrays.stream(newCommand.split("\\|")).collect(Collectors.toList());
        return newParameters;
    }

    private List<String> removeStepsFromList(List<String> parameters) {
        String fullCommand = parameters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

        String newCommand = fullCommand.replaceAll("\\{.+}", "");
        return Arrays.stream(newCommand.split("\\s+")).collect(Collectors.toList());
    }

    private void addBugToBoard(String title, String description, PriorityType priority, SeverityType severity, List<String> stepsToReproduce, Board board, String teamName) {
        Bug bug = factory.createBug(title, description, String.valueOf(priority), String.valueOf(severity),stepsToReproduce);
        repository.findBoard(board.getName(), teamName).addItemToBoard(bug);
    }

    private void addToBoardHistory(String title, Board board) {
        board.addToHistory(String.format("Bug with name %s has been created in board %s", title, board.getName()));
    }

    private String executionMessage(String title, Board board) {
        return String.format("Bug with name %s has been created in board %s", title, board.getName());
    }

}
