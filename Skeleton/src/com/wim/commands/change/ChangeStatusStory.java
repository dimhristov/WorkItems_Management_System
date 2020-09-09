
package com.wim.commands.change;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.BugStatusType;
import com.wim.models.common.enums.StoryStatusType;
import com.wim.models.contracts.*;

import java.util.List;

import static com.wim.ValidationHelper.validateArgumentIsNotNull;
import static com.wim.models.common.constants.Constants.*;

public class ChangeStatusStory extends CommandImpl implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private final ItemManagementRepository itemRepository;

    private String personName;
    private String boardName;
    private String teamName;
    private String title;
    private String statusType;

    public ChangeStatusStory (ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String execute (List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return changeStatusStory(personName, boardName, teamName, title, statusType);
    }

    private void parseParameters (List<String> parameters) {
        try {
            personName = parameters.get(0);
            boardName = parameters.get(1);
            teamName = parameters.get(2);
            title = parameters.get(3);
            statusType = parameters.get(4).toUpperCase();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse ChangeStatusStory command parameters.");
        }
    }

    private String changeStatusStory (String personName, String boardName, String teamName, String title, String statusType) {

        if (!itemRepository.getMembers().containsKey(personName)) {
            throw new IllegalArgumentException(String.format(PERSON_NOT_EXISTS_ERROR_MESSAGE, personName));
        }
        if (!itemRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_NOT_EXISTS_ERROR_MESSAGE, teamName));
        }

        Member member = itemRepository.getMembers().get(personName);
        Board board = itemRepository.findBoard(boardName, teamName);
        Team team = itemRepository.getTeams().get(teamName);
        Story story = itemRepository.findBoard(boardName, teamName).findStory(title);

        if (!(team.getMemberList().contains(member))) {
            throw new IllegalArgumentException(String.format("Member %s is not present in team %s", member.getName(), team.getName()));
        }
        if (!team.getBoardList().contains(board)) {
            throw new IllegalArgumentException(String.format("Board with name %s is not present in team %s", board.getName(), team.getName()));
        }
        if (!board.getWorkItems().contains(story)) {
            throw new IllegalArgumentException(String.format("Item %s is not present in board %s", story.getTitle(), board.getName()));
        }

        itemRepository.findBoard(board.getName(), team.getName()).findStory(title).changeStatusStory(statusType);
        return String.format("Status of story %s has been changed to %s", story.getTitle(), StoryStatusType.valueOf(statusType));
    }

}



