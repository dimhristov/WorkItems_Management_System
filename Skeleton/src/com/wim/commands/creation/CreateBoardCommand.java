package com.wim.commands.creation;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementFactory;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Team;

import java.util.List;

import static com.wim.commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;

public class CreateBoardCommand extends CommandImpl implements Command {
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse CreateBoard command parameters.";
    private static final String EXECUTION_MESS = "Board with name %s has been created!";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private final ItemManagementFactory factory;
    private final ItemManagementRepository repository;

    private String boardName;
    private String teamName;

    public CreateBoardCommand (ItemManagementFactory factory, ItemManagementRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    @Override
    public String execute (List<String> parameters) {
        validateInput(parameters,EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        createBoard(boardName, teamName);
        return executionMessage(boardName);
    }

    private void parseParameters (List<String> parameters) {
        try {
            boardName = parameters.get(0);
            teamName=parameters.get(1);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private void createBoard(String boardName, String teamName) {
        Board board = factory.createBoard(boardName);
        repository.getTeam(teamName).addBoard(board);
    }

    private String executionMessage(String boardName) {
        return String.format(EXECUTION_MESS, boardName);
    }


}
