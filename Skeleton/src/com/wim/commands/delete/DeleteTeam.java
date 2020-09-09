package com.wim.commands.delete;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;

import java.util.List;

public class DeleteTeam  extends CommandImpl implements Command {
    private static final String EXECUTE_MESSAGE = "Team with name %s has been deleted";
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse DeleteTeam command parameters.\nExample: DeleteMember [memberName]";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private final ItemManagementRepository repository;
    private Team team;

    public DeleteTeam(ItemManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters,EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        deleteAllMembersFromTeam(team);
        moveTeamToTrashBin(team);
        deleteTeam(team);
        return executionMessage();
    }

    private void parseParameters(List<String> parameters) {
        try {
            team = repository.getTeam(parameters.get(0));
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private void deleteAllMembersFromTeam(Team team) {
        List<Member> teamMembers = repository.getTeam(team.getName()).getMemberList();
        for (Member teamMember : teamMembers) {
            deleteFromRepository(teamMember);
        }
        repository.getTeam(team.getName()).getMemberList().clear();
    }

    private void moveTeamToTrashBin(Team team) {
        repository.getArchive().getTeamArchive().put(team.getName(),team);
    }

    private void deleteTeam(Team team) {
        repository.getTeams().remove(team.getName());
    }

    private String executionMessage() {
        return String.format(EXECUTE_MESSAGE,team.getName());
    }

    private void deleteFromRepository(Member member) {
        repository.getMembers().remove(member.getName());
    }
}
