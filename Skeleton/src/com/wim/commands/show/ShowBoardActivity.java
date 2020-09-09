package com.wim.commands.show;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;

import java.util.List;

public class ShowBoardActivity extends CommandImpl implements Command {
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse showBoardActivity command parameters.";
    private static final String TEAM_NOT_EXIST = "Team with name %s doesn't exist";
    private static final String BOARD_NOT_EXIST = "Board with name %s doesn't exist";
    private static final String ACTIVITY_HISTORY_IS_EMPTY = "Activity in board %s in team %s is empty";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final ItemManagementRepository itemRepository;
    private String boardName;
    private String teamName;

    public ShowBoardActivity(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return showBoardActivity(boardName);
    }

    private void parseParameters(List<String> parameters) {
        try {
            boardName = parameters.get(0);
            teamName = parameters.get(1);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private String showBoardActivity(String boardName) {
        if (!itemRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_NOT_EXIST, teamName));
        }
        if (itemRepository.getTeam(teamName).getBoard(boardName) == null) {
            throw new IllegalArgumentException(String.format(BOARD_NOT_EXIST, boardName));
        }
        if (itemRepository.findBoard(boardName, teamName).getActivityHistory().isEmpty()) {
            throw new IllegalArgumentException(String.format(ACTIVITY_HISTORY_IS_EMPTY, boardName, teamName));
        }
        List<String> activity = itemRepository.findBoard(boardName, teamName).getActivityHistory();
        return printListActivity(activity);
    }

}
