package com.wim.commands.delete;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;

import java.util.List;

public class DeleteBoard extends CommandImpl implements Command {

    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse DeleteBoard command parameters.\nExample: DeleteBoard [boardName]";
    private static final String EXECUTION_MESSAGE = "Board with name %s has been deleted";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private final ItemManagementRepository repository;
    private Board board;
    private Team team;

    public DeleteBoard(ItemManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters,EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        moveBoardToTrashBin(board);
        deleteBoard(board);
        return executionMessage();
    }

    private void parseParameters(List<String> parameters) {
        try {
            board = repository.findBoard(parameters.get(0),parameters.get(1));
            team = repository.getTeam(parameters.get(1));
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private void moveBoardToTrashBin(Board board) {
        repository.getArchive().getBoardArchive().put(board.getName(),board);
    }

    private String executionMessage() {
        return String.format(EXECUTION_MESSAGE, board.getName());
    }

    private void deleteBoard(Board board) {
        repository.getTeam(team.getName()).getBoardList().remove(board);
    }
}
