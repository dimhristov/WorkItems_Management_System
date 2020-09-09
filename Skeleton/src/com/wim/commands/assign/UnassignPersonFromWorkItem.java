package com.wim.commands.assign;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.SeverityType;
import com.wim.models.contracts.*;

import java.util.List;

import static com.wim.ValidationHelper.validateArgumentIsNotNull;
import static com.wim.models.common.constants.Constants.*;

public class UnassignPersonFromWorkItem extends CommandImpl implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private static final String FAILED_TO_PARSE_ERR_MESS = "Failed to parse UnassignedWorkItemToPerson command parameters.";
    private static final String MEMBER_NOT_PRESENT_IN_TEAM = "Member with name %s is not present in team %s";

    private final ItemManagementRepository itemRepository;

    private String personName;
    private String boardName;
    private String teamName;
    private String itemName;

    public UnassignPersonFromWorkItem(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String execute (List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return unassignWorkItemToPerson(personName, boardName, teamName, itemName);
    }

    private void parseParameters (List<String> parameters) {
        try {
            personName = parameters.get(0);
            boardName = parameters.get(1);
            teamName = parameters.get(2);
            itemName = parameters.get(3);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_TO_PARSE_ERR_MESS);
        }
    }

    private String unassignWorkItemToPerson (String personName, String boardName, String teamName, String itemName) {

        if (!itemRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_NOT_EXISTS_ERROR_MESSAGE, teamName));
        }
        if (!itemRepository.getTeam(teamName).getMemberList().contains(itemRepository.getMember(personName))) {
            throw new IllegalArgumentException(String.format(MEMBER_NOT_PRESENT_IN_TEAM, personName,teamName));
        }

        Member member = itemRepository.getTeam(teamName).getMember(personName);
        Board board = itemRepository.findBoard(boardName, teamName);
        Team team = itemRepository.getTeams().get(teamName);
        WorkItem item = itemRepository.findBoard(boardName, teamName).getItem(itemName);

        if (!team.getBoardList().contains(board)) {
            throw new IllegalArgumentException(String.format("Board with name %s is not present in team %s", board.getName(), team.getName()));
        }
        if (!board.getWorkItems().contains(item)) {
            throw new IllegalArgumentException(String.format("Item with title %s is not present in board %s", item.getTitle(), board.getName()));
        }
        if (item instanceof Bug) {
            itemRepository.findBoard(boardName,teamName).findBug(itemName).getListAssignee().remove(member);
        } else if (item instanceof Story) {
            itemRepository.findBoard(boardName,teamName).findStory(itemName).getListAssignee().remove(member);
        }

        member.getWorkItems().remove(item);

        String message = String.format("Member %s has been unassigned from item %s",member.getName(),item.getTitle());
        member.addToHistory(message);
        board.addToHistory(message);
        return message;
    }


}
