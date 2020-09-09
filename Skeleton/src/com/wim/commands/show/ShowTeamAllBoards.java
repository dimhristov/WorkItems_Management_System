package com.wim.commands.show;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Team;

import java.util.List;

import static com.wim.models.common.constants.Constants.*;

public class ShowTeamAllBoards extends CommandImpl implements Command {

    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse showTeamAllBoards command parameters.";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private final ItemManagementRepository itemRepository;
    private String teamName;

    public ShowTeamAllBoards(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return showAllTeamBoards(teamName);
    }

    private void parseParameters(List<String> parameters) {
        try {
            teamName = parameters.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private String showAllTeamBoards(String teamName) {

        if (!itemRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_NOT_EXISTS_ERROR_MESSAGE, teamName));
        }
        Team team = itemRepository.getTeams().get(teamName);
        List<Board> boards = team.getBoardList();

        if (boards.size() == 0) {
            return String.format(NO_BOARDS_IN_TEAM, teamName);
        }
        return boards.toString().replaceAll("[\\[\\]]","");
    }

}
