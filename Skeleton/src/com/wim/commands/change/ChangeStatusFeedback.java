package com.wim.commands.change;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.FeedbackStatusType;
import com.wim.models.common.enums.StoryStatusType;
import com.wim.models.contracts.*;

import java.util.List;

import static com.wim.models.common.constants.Constants.PERSON_NOT_EXISTS_ERROR_MESSAGE;
import static com.wim.models.common.constants.Constants.TEAM_NOT_EXISTS_ERROR_MESSAGE;

public class ChangeStatusFeedback extends CommandImpl implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private final ItemManagementRepository itemRepository;

    private String personName;
    private String boardName;
    private String teamName;
    private String title;
    private String feedbackType;

    public ChangeStatusFeedback (ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String execute (List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return changeStatusFeedback(personName, boardName, teamName, title, feedbackType);
    }

    private void parseParameters (List<String> parameters) {
        try {
            personName = parameters.get(0);
            boardName = parameters.get(1);
            teamName = parameters.get(2);
            title = parameters.get(3);
            feedbackType = parameters.get(4).toUpperCase();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse ChangeStatusFeedback command parameters.");
        }
    }

    private String changeStatusFeedback (String personName, String boardName, String teamName, String title, String feedbackType) {

        if (!itemRepository.getMembers().containsKey(personName)) {
            throw new IllegalArgumentException(String.format(PERSON_NOT_EXISTS_ERROR_MESSAGE, personName));
        }
        if (!itemRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_NOT_EXISTS_ERROR_MESSAGE, teamName));
        }

        Member member = itemRepository.getMembers().get(personName);
        Board board = itemRepository.findBoard(boardName, teamName);
        Team team = itemRepository.getTeams().get(teamName);
        Feedback feedback=  itemRepository.getTeam(teamName).getBoard(board.getName()).findFeedback(title);


        if (!(team.getMemberList().contains(member))) {
            throw new IllegalArgumentException(String.format("Member %s is not present in team %s", member.getName(), team.getName()));
        }
        if (!team.getBoardList().contains(board)) {
            throw new IllegalArgumentException(String.format("Board with name %s is not present in team %s", board.getName(), team.getName()));
        }
        if (!board.getWorkItems().contains(feedback)) {
            throw new IllegalArgumentException(String.format("Item %s is not present in board %s", feedback.getTitle(), board.getName()));
        }

        itemRepository.findBoard(board.getName(), team.getName()).findFeedback(title).changeStatusFeedback(feedbackType);
        return String.format("Status of feedback %s has been changed to %s", feedback.getTitle(), FeedbackStatusType.valueOf(feedbackType));
    }
}
