package com.wim.commands.change;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.BugStatusType;
import com.wim.models.common.enums.SeverityType;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;
import com.wim.models.contracts.WorkItem;

import java.util.List;

import static com.wim.ValidationHelper.validateArgumentIsNotNull;
import static com.wim.models.common.constants.Constants.*;

public class ChangeBugStatus extends CommandImpl implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private final ItemManagementRepository itemRepository;

    private String personName;
    private String boardName;
    private String teamName;
    private String itemName;
    private String status;

    public ChangeBugStatus(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        validateArgumentIsNotNull(itemRepository.findBoard(boardName, teamName).findBug(itemName),
                String.format("Board with %s doesn't exist!", itemName));
        return changeBugStatus(personName, boardName, teamName, itemName, status);
    }

    private void parseParameters(List<String> parameters) {
        try {
            personName = parameters.get(0);
            boardName = parameters.get(1);
            teamName = parameters.get(2);
            itemName = parameters.get(3);
            status = parameters.get(4).toUpperCase();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse ChangeStatus command parameters.");
        }
    }

    private String changeBugStatus(String personName, String boardName, String teamName, String itemName, String status) {
        BugStatusType bugStatusType = BugStatusType.valueOf(status.toUpperCase());

        if (!itemRepository.getMembers().containsKey(personName)) {
            throw new IllegalArgumentException(String.format(PERSON_NOT_EXISTS_ERROR_MESSAGE, personName));
        }

        if (!itemRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_NOT_EXISTS_ERROR_MESSAGE, teamName));
        }

        Member member = itemRepository.getMembers().get(personName);
        Board board = itemRepository.findBoard(boardName, teamName);
        Team team = itemRepository.getTeams().get(teamName);
        WorkItem item = itemRepository.findBoard(boardName, teamName).getItem(itemName);


        if (!(team.getMemberList().contains(member))) {
            throw new IllegalArgumentException(String.format("Member %s is not present in team %s", member.getName(), team.getName()));
        }
        if (!team.getBoardList().contains(board)) {
            throw new IllegalArgumentException(String.format("Board with name %s is not present in team %s", board.getName(), team.getName()));
        }
        if (!board.getWorkItems().contains(item)) {
            throw new IllegalArgumentException(String.format("Item %s is not present in board %s", item.getTitle(), board.getName()));
        }

        itemRepository.findBoard(board.getName(), team.getName()).findBug(item.getTitle()).changeBugStatus(status);
        return String.format("Severity of bug %s has been changed to %s", item.getTitle(), BugStatusType.valueOf(status));
    }

}
