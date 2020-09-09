package com.wim.commands.creation;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementFactory;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Feedback;

import java.util.List;

public class CreateFeedbackCommand extends CommandImpl implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private static final String EXECUTION_MESS = "Feedback with name %s has been created in board %s";

    private final ItemManagementFactory factory;
    private final ItemManagementRepository repository;

    private String title;
    private String description;
    private int rating;
    private String boardName;
    private String teamName;

    public CreateFeedbackCommand(ItemManagementFactory factory, ItemManagementRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<String> newParameters = removeDescriptionFromList(parameters);
        validateInput(newParameters,EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        addFeedback(title, description, rating);
        addToBoardHistory(boardName, teamName, title);
        return executionMessage(title, boardName);
    }

    private void addToBoardHistory(String boardName, String teamName, String title) {
        repository.findBoard(boardName,teamName).addToHistory(executionMessage(title,boardName));
    }

    private String executionMessage(String title, String boardName) {
        return String.format(EXECUTION_MESS,title, boardName);
    }

    private void parseParameters(List<String> parameters) {

        try {
            description = separateDescriptionFromList(parameters);
        } catch (Exception e) {
            throw new IllegalArgumentException("Please enter description in brackets [Lorem ipsum dolor sit amet...]");
        }

        List<String> newParameters = removeDescriptionFromList(parameters);
        try {
            title = newParameters.get(0);
            rating=Integer.parseInt(newParameters.get(1));
            teamName=newParameters.get(2);
            boardName = newParameters.get(3);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse CreateFeedback command parameters.");
        }
    }

    private void addFeedback(String title, String description, int rating) {
        Feedback feedback = factory.createFeedback(title, description, rating);
        repository.findBoard(boardName,teamName).addItemToBoard(feedback);
    }
}

