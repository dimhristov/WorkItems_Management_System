package com.wim.commands.add;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.*;
import com.wim.models.implementation.CommentImpl;

import java.util.List;

public class AddCommentToWorkItem extends CommandImpl implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private static final String EXECUTION_MESSAGE = "Member %s has added comment in board %s, to work item %s";
    private static final String VALID_COMMAND_ERR_MESS = "Please enter your message in brackets [Lorem ipsum dolor sit amet...]";
    private static final String FAILED_TO_PARSE_ERR_MESS = "Failed to parse CreateBug command parameters.";

    private final ItemManagementRepository repository;
    private String memberName;
    private String boardName;
    private String teamName;
    private String itemName;
    private String message;

    public AddCommentToWorkItem(ItemManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<String> newParameters = removeDescriptionFromList(parameters);
        validateInput(newParameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        addComment(memberName, teamName, itemName);
        addToMemberHistory(teamName, memberName);
        addToItemHistory(teamName, boardName, itemName);
        return executionMessage();
    }

    private void parseParameters(List<String> parameters) {
        try {
            message = separateDescriptionFromList(parameters);
        } catch (Exception e) {
            throw new IllegalArgumentException(VALID_COMMAND_ERR_MESS);
        }
        List<String> newParameters = removeDescriptionFromList(parameters);
        try {
            memberName = newParameters.get(0);
            boardName = newParameters.get(1);
            teamName = newParameters.get(2);
            itemName = newParameters.get(3);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_TO_PARSE_ERR_MESS);
        }
    }

    private void addComment(String memberName, String teamName, String itemName) {
        Member member = repository.getMember(memberName);
        Comment comment = new CommentImpl(member, message);
        repository.getTeam(teamName).getBoard(boardName).getItem(itemName).addComment(comment);
    }

    private void addToMemberHistory(String teamName, String memberName) {
        repository.getTeam(teamName).getMember(memberName).addToHistory(String.format(EXECUTION_MESSAGE,
                memberName, boardName, itemName));
    }

    private void addToItemHistory(String teamName, String boardName, String itemName) {
        repository.getTeam(teamName).getBoard(boardName).getItem(itemName).addToHistory(String.format(EXECUTION_MESSAGE,
                memberName, boardName, itemName));
    }

    private String executionMessage() {
        return String.format(EXECUTION_MESSAGE,
                memberName, boardName, itemName);
    }
}
