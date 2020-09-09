package com.wim.commands.delete;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Member;

import java.util.List;

public class DeleteMember extends CommandImpl implements Command {
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse DeleteMember command parameters.\nExample: DeleteMember [memberName]";
    private static final String EXECUTION_MESSAGE = "Member with name %s has been deleted";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private final ItemManagementRepository repository;
    private Member member;

    public DeleteMember(ItemManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters,EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        moveToTrashBin(member);
        deleteFromRepository(member);
        return executionMessage();
    }

    private void parseParameters(List<String> parameters) {
        try {
            member = repository.getMember(parameters.get(0));
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private void moveToTrashBin(Member member) {
        repository.getArchive().getMemberArchive().put(member.getName(),member);
    }

    private void deleteFromRepository(Member member) {
        repository.getMembers().remove(member.getName());
    }

    private String executionMessage() {
        return String.format(EXECUTION_MESSAGE,member.getName());
    }
}
