package com.wim.commands.change;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.StorySizeType;
import com.wim.models.common.enums.StoryStatusType;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Story;
import com.wim.models.contracts.Team;

import java.util.List;

import static com.wim.models.common.constants.Constants.PERSON_NOT_EXISTS_ERROR_MESSAGE;
import static com.wim.models.common.constants.Constants.TEAM_NOT_EXISTS_ERROR_MESSAGE;

public class ChangeSizeStory extends CommandImpl implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private final ItemManagementRepository itemRepository;

    private String personName;
    private String boardName;
    private String teamName;
    private String title;
    private String sizeType;

    public ChangeSizeStory (ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String execute (List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return changeSizeStory(personName, boardName, teamName, title, sizeType);
    }

    private void parseParameters (List<String> parameters) {
        try {
            personName = parameters.get(0);
            boardName = parameters.get(1);
            teamName = parameters.get(2);
            title = parameters.get(3);
            sizeType = parameters.get(4).toUpperCase();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse ChangeStatusStory command parameters.");
        }
    }

    private String changeSizeStory (String personName, String boardName, String teamName, String title, String sizeType) {

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

        itemRepository.findBoard(board.getName(), team.getName()).findStory(title).changeSizeStory(sizeType);
        return String.format("Size of story %s has been changed to %s", story.getTitle(), StorySizeType.valueOf(sizeType));
    }

}
