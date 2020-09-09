package com.wim.commands.show;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Member;

import java.util.ArrayList;
import java.util.List;

import static com.wim.models.common.constants.Constants.NO_EXISTING_PEOPLE;

public class ShowAllTeamMembers extends CommandImpl implements Command {
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse showTeamAllMembers command parameters.";
    private static final String TEAM_NOT_EXIST = "Team with name %s doesn't exist";
    private static final String TEAM_DOESNT_HAVE_ANY_MEMBERS = "Team %s doesn't have any members.\n" +
                          "You can add members using addMemberToTeam [memberName] [teamName] command";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final ItemManagementRepository itemRepository;
    private String teamName;

    public ShowAllTeamMembers(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return showBoardActivity(teamName);
    }

    private void parseParameters(List<String> parameters) {
        try {
            teamName = parameters.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private String showBoardActivity(String teamName) {
        if (!itemRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_NOT_EXIST, teamName));
        }
        if (itemRepository.getTeam(teamName).getMemberList().isEmpty()) {
            return String.format(TEAM_DOESNT_HAVE_ANY_MEMBERS,teamName);
        }
        List<Member> allMembers = new ArrayList<>(itemRepository.getTeam(teamName).getMemberList());
        String removedCommas = allMembers.toString().replaceAll(",[\\s]*", "");
        return removedCommas.replaceAll("[\\[\\]]","");
    }



}
