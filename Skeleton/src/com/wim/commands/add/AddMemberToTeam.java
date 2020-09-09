package com.wim.commands.add;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;

import java.util.List;

import static com.wim.commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;

public class AddMemberToTeam extends CommandImpl implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String EXECUTION_MESS = "Member with name %s has been added to team %s";
    private static final String TEAM_OR_MEMBER_NOT_EXIST = "Team or member with the following names doesn't exist: Team: %s | Member: %s";
    private static final String FAILED_TO_PARSE_ERR_MESS = "Failed to parse AddMemberToTeam command parameters.";

    private final ItemManagementRepository repository;
    private Member member;
    private Team team;

    public AddMemberToTeam(ItemManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters,EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return addMemberToTeam();
    }

    private void parseParameters(List<String> parameters) {
        try {
            member = repository.getMember(parameters.get(0));
            team = repository.getTeam(parameters.get(1));
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_TO_PARSE_ERR_MESS);
        }
    }

    private String addMemberToTeam() {
        if (repository.getTeams().containsKey(team.getName()) && repository.getMembers().containsKey(member.getName())) {
            repository.getTeam(team.getName()).addMemberToTeam(member);
            return String.format(EXECUTION_MESS, member.getName(), team.getName());
        } else {
            return String.format(TEAM_OR_MEMBER_NOT_EXIST, team.getName(), member.getName());
        }
    }
}
