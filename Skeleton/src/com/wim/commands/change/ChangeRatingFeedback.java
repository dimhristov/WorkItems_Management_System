package com.wim.commands.change;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.StorySizeType;
import com.wim.models.contracts.*;

import java.util.List;

import static com.wim.models.common.constants.Constants.PERSON_NOT_EXISTS_ERROR_MESSAGE;
import static com.wim.models.common.constants.Constants.TEAM_NOT_EXISTS_ERROR_MESSAGE;

public class ChangeRatingFeedback extends CommandImpl implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse ChangeRatingFeedback command parameters.";
    private static final String MEMBER_NOT_PRESENT_MESS = "Member %s is not present in team %s";
    private static final String BOAR_NOT_PRESENT_MESS = "Board with name %s is not present in team %s";
    private static final String FEEDBACK_NOT_PRESENT_MESS = "Feedback %s is not present in board %s";
    private static final String EXECUTION_MESSAGE = "Rating of feedback %s has been changed to %d";

    private final ItemManagementRepository itemRepository;

    private String personName;
    private String boardName;
    private String teamName;
    private String title;
    private int rating;

    public ChangeRatingFeedback (ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String execute (List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return changeRatingFeedback(personName, boardName, teamName, title, rating);
    }

    private void parseParameters (List<String> parameters) {
        try {
            personName = parameters.get(0);
            boardName = parameters.get(1);
            teamName = parameters.get(2);
            title = parameters.get(3);
            rating = Integer.parseInt(parameters.get(4));
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private String changeRatingFeedback (String personName, String boardName, String teamName, String title, int rating) {

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
            throw new IllegalArgumentException(String.format(MEMBER_NOT_PRESENT_MESS, member.getName(), team.getName()));
        }
        if (!team.getBoardList().contains(board)) {
            throw new IllegalArgumentException(String.format(BOAR_NOT_PRESENT_MESS, board.getName(), team.getName()));
        }
        if (!board.getWorkItems().contains(feedback)) {
            throw new IllegalArgumentException(String.format(FEEDBACK_NOT_PRESENT_MESS, feedback.getTitle(), board.getName()));
        }
        itemRepository.findBoard(board.getName(), team.getName()).findFeedback(title).changeRatingFeedback(rating);
        return String.format(EXECUTION_MESSAGE, feedback.getTitle(),rating);
    }
}


