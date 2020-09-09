package com.wim.commands.creation;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementFactory;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.PriorityType;
import com.wim.models.common.enums.StorySizeType;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Story;

import java.util.List;

public class CreateStoryCommand extends CommandImpl implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;
    private static final String FAILED_PARSE_DESCRIPTION_ERR_MESS = "Please enter description in brackets [Lorem ipsum dolor sit amet...]";
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse CreateStory command parameters.";
    private static final String EXECUTION_MESS = "Story with name %s has been created in board %s";

    private final ItemManagementFactory factory;
    private final ItemManagementRepository repository;

    private String title;
    private String description;
    private StorySizeType storySizeType;
    private Board board;
    private String teamName;
    private PriorityType priority;

    public CreateStoryCommand(ItemManagementFactory factory, ItemManagementRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<String> newParameters = removeDescriptionFromList(parameters);
        validateInput(newParameters,EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        createStory(title, description, storySizeType, board, teamName, priority);
        addToBoardHistory(title, board);
        return executionMessage(title, board);
    }

    private void parseParameters(List<String> parameters) {
        try {
            description = separateDescriptionFromList(parameters);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(FAILED_PARSE_DESCRIPTION_ERR_MESS);
        }
        List<String> newParameters = removeDescriptionFromList(parameters);
        try {
            title = newParameters.get(0);
            storySizeType = StorySizeType.valueOf(newParameters.get(1).toUpperCase());
            teamName=newParameters.get(2);
            board = repository.findBoard(newParameters.get(3),teamName);
            priority = PriorityType.valueOf(newParameters.get(4).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private void createStory(String title, String description, StorySizeType storySizeType,
                             Board board, String teamName, PriorityType priority) {
        Story story = factory.createStory(title, description, String.valueOf(storySizeType),String.valueOf(priority));
        board.addItemToBoard(story);
    }

    private void addToBoardHistory(String title, Board board) {
        board.addToHistory(String.format(EXECUTION_MESS,title,board.getName()));
    }

    private String executionMessage(String title, Board board) {
        return String.format(EXECUTION_MESS,title,board.getName());
    }


}
