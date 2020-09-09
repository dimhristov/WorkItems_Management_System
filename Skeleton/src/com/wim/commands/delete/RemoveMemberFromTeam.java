package com.wim.commands.delete;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;
import java.util.List;

public class RemoveMemberFromTeam extends CommandImpl implements Command {
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse RemoveMemberFromTeam command parameters.\nExample: RemoveMemberFromTeam [memberName] [teamName]";
    private static final String EXECUTION_MESSAGE = "Member with name %s has been removed from team %s";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private final ItemManagementRepository repository;
    private Member member;
    private Team team;

    public RemoveMemberFromTeam(ItemManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        removeMemberFromAsignedTasks(member, team);
        removeMemberFromTeam(member, team);
        moveToTrashBin(member);
        deleteFromRepository(member);
        return executionMessage();
    }

    private void parseParameters(List<String> parameters) {
        try {
            member = repository.getMember(parameters.get(0));
            team = repository.getTeam(parameters.get(1));
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private void removeMemberFromTeam(Member member, Team team) {
        repository.getTeam(team.getName()).removeMemberFromTeam(member);
    }

    private void removeMemberFromAsignedTasks(Member member, Team team) {
        repository.getTeam(team.getName()).getBoardList().stream()
                .flatMap(b -> b.getWorkItems().stream())
                .filter(i -> i.getAsignee(member.getName()).equalsIgnoreCase(member.getName()))
                .forEach(i -> i.removeAsignee(member));
    }

    private String executionMessage() {
        return  String.format(EXECUTION_MESSAGE, member.getName(), team.getName());
    }

    private void deleteFromRepository(Member member) {
        repository.getMembers().remove(member.getName());
    }

    private void moveToTrashBin(Member member) {
        repository.getArchive().getMemberArchive().put(member.getName(),member);
    }
}
