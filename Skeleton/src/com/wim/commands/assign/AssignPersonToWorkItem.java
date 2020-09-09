package com.wim.commands.assign;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.SeverityType;
import com.wim.models.contracts.*;

import java.util.List;

import static com.wim.ValidationHelper.validateArgumentIsNotNull;
import static com.wim.models.common.constants.Constants.*;

public class AssignPersonToWorkItem extends CommandImpl implements Command {
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse AssignWorkItemToPerson command parameters.";

    private static final String MEMBER_DONT_EXIST_IN_TEAM = "Member with name %s is not present in team %s";
    private static final String BOARD_NOT_PRESENT = "Board with name %s is not present in team %s";
    private static final String ITEM_NOT_PRESENT = "Item with title %s is not present in board %s";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    private final ItemManagementRepository itemRepository;

    private String personName;
    private String boardName;
    private String teamName;
    private String itemName;

    public AssignPersonToWorkItem(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return assignWorkItemToPerson(personName, boardName, teamName, itemName);
    }

    private void parseParameters(List<String> parameters) {
        try {
            personName = parameters.get(0);
            boardName = parameters.get(1);
            teamName = parameters.get(2);
            itemName = parameters.get(3);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private String assignWorkItemToPerson(String personName, String boardName, String teamName, String itemName) {

        if (!itemRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_NOT_EXISTS_ERROR_MESSAGE, teamName));
        }
        if (!itemRepository.getTeam(teamName).getMemberList().contains(itemRepository.getMember(personName))) {
            throw new IllegalArgumentException(String.format(MEMBER_DONT_EXIST_IN_TEAM, personName,teamName));
        }

        Team team = itemRepository.getTeams().get(teamName);
        Member member = itemRepository.getMember(personName);
        Board board = itemRepository.findBoard(boardName, teamName);
        WorkItem item = board.getItem(itemName);

        if (!team.getBoardList().contains(board)) {
            throw new IllegalArgumentException(String.format(BOARD_NOT_PRESENT, board.getName(), team.getName()));
        }
        if (!board.getWorkItems().contains(item)) {
            throw new IllegalArgumentException(String.format(ITEM_NOT_PRESENT, item.getTitle(), board.getName()));
        }

        if (item instanceof Story) {
            itemRepository.getTeam(team.getName()).getBoard(board.getName()).findStory(item.getTitle()).assignMember(member);
        } else if (item instanceof Bug) {
            itemRepository.getTeam(team.getName()).getBoard(board.getName()).findBug(item.getTitle()).assignMember(member);
        }
        itemRepository.getTeam(teamName).getMember(member.getName()).addWorkItem(item);
        //itemRepository.findBoard(boardName,teamName).getItem(itemName).assignMember(member);
        //member.addWorkItem(item);

        String message = String.format("Member %s is assigned on item %s",member.getName(),item.getTitle());
        member.addToHistory(message);
        board.addToHistory(message);
        return message;
    }
}
