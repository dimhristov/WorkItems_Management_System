package com.wim.commands.show;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;

import java.util.List;
import java.util.stream.Collectors;

public class ShowTeamActivity extends CommandImpl implements Command {
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse showTeamActivity command parameters.";
    private static final String NO_ACTIVITY_MESS = "Team %s doesn't have any activity yet";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final ItemManagementRepository itemRepository;
    private String teamName;

    public ShowTeamActivity(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return showTeamActivity(teamName);
    }

    private void parseParameters(List<String> parameters) {
        try {
            teamName = parameters.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private String showTeamActivity(String teamName) {

        Team team = itemRepository.getTeam(teamName);
        List<String> activityList = team.getBoardList()
                .stream()
                .flatMap(b -> b.getActivityHistory().stream())
                .collect(Collectors.toList());
        if (activityList.isEmpty()) {
            return String.format(NO_ACTIVITY_MESS, teamName);
        } else {
            return printListActivity(activityList);
        }

    }
}
