package com.wim.commands.delete;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Team;
import com.wim.models.contracts.WorkItem;

import java.util.List;

public class DeleteWorkItem extends CommandImpl implements Command {

    private static final String EXECUTION_MESSAGE = "Item with name %s has been deleted from board %s in team %s";
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse DeleteWorkItem command parameters.\nExample: DeleteWorkItem [itemName] [boardName] [teamName]";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    private final ItemManagementRepository repository;
    private WorkItem item;
    private Board board;
    private Team team;

    public DeleteWorkItem(ItemManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        moveItemToTrashBin(item);
        deleteItemFromBoard(item, board, team);
        addToBoardActivityHistory(board, team);
        return executionMessage(item, board, team);
    }

    private void parseParameters(List<String> parameters) {
        try {
            board = repository.findBoard(parameters.get(1), parameters.get(2));
            team = repository.getTeam(parameters.get(2));
            item = repository.getTeam(parameters.get(2)).getBoard(parameters.get(1)).getItem(parameters.get(0));
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private void moveItemToTrashBin(WorkItem item) {
        repository.getArchive().getWorkItemsArchive().put(item.getTitle(), item);
    }

    private void deleteItemFromBoard(WorkItem item, Board board, Team team) {
        repository.findBoard(board.getName(), team.getName()).getWorkItems().remove(item);
    }

    private String executionMessage(WorkItem item, Board board, Team team) {
        return String.format(EXECUTION_MESSAGE, item.getTitle(), board.getName(), team.getName());
    }

    private void addToBoardActivityHistory(Board board, Team team) {
        repository.findBoard(board.getName(),team.getName())
                .getActivityHistory()
                .add(String.format(EXECUTION_MESSAGE, item.getTitle(), board.getName(), team.getName()));
    }

}
