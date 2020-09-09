package com.wim.core.factories;

import com.wim.commands.add.AddCommentToWorkItem;
import com.wim.commands.add.AddMemberToTeam;
import com.wim.commands.assign.AssignPersonToWorkItem;
import com.wim.commands.assign.UnassignPersonFromWorkItem;
import com.wim.commands.change.*;
import com.wim.commands.contracts.Command;
import com.wim.commands.creation.*;
import com.wim.commands.delete.*;
import com.wim.commands.enums.CommandType;
import com.wim.commands.list.*;
import com.wim.commands.show.*;
import com.wim.core.contracts.CommandFactory;
import com.wim.core.contracts.ItemManagementFactory;
import com.wim.core.contracts.ItemManagementRepository;

import java.util.Arrays;

public class CommandFactoryImpl implements CommandFactory {

    private static final String INVALID_COMMAND = "Invalid command name: %s!";


    public Command createCommand(String commandName, ItemManagementFactory itemManagementFactory,
                                 ItemManagementRepository itemManagementRepository) {
//        boolean exists = Arrays.stream(CommandType.values())
//                .anyMatch(c -> c.toString().equals(commandName.toUpperCase()));
//
        CommandType commandType = Arrays.stream(CommandType.values())
                .filter(c -> c.toString().equalsIgnoreCase(commandName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(INVALID_COMMAND, commandName)));


        switch (commandType) {

            case CREATETEAM:
                return new CreateTeamCommand(itemManagementFactory, itemManagementRepository);
            case CREATEMEMBER:
                return new CreateMemberCommand(itemManagementFactory, itemManagementRepository);
            case CREATEBOARD:
                return new CreateBoardCommand(itemManagementFactory, itemManagementRepository);
            case CREATEBUG:
                return new CreateBugCommand(itemManagementFactory, itemManagementRepository);
            case CREATESTORY:
                return new CreateStoryCommand(itemManagementFactory, itemManagementRepository);
            case CREATEFEEDBACK:
                return new CreateFeedbackCommand(itemManagementFactory, itemManagementRepository);
            case SHOWALLMEMBERS:
                return new ShowAllMembers(itemManagementRepository);
            case SHOWALLTEAMS:
                return new ShowAllTeams(itemManagementRepository);
            case SHOWTEAMALLBOARDS:
                return new ShowTeamAllBoards(itemManagementRepository);
            case CHANGEPRIORITY:
                return new ChangePriority(itemManagementRepository);
            case ADDMEMBERTOTEAM:
                return new AddMemberToTeam(itemManagementRepository);
            case ADDCOMMENTTOWORKITEM:
                return new AddCommentToWorkItem(itemManagementRepository);
            case CHANGEBUGSEVERITY:
                return new ChangeBugSeverity(itemManagementRepository);
            case CHANGEBUGSTATUS:
                return new ChangeBugStatus(itemManagementRepository);
            case ASSIGNPERSONTOWORKITEM:
                return new AssignPersonToWorkItem(itemManagementRepository);
            case UNASSIGNPERSONFROMWORKITEM:
                return new UnassignPersonFromWorkItem(itemManagementRepository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivity(itemManagementRepository);
            case CHANGESTATUSSTORY:
                return new ChangeStatusStory(itemManagementRepository);
            case CHANGESIZESTORY:
                return new ChangeSizeStory(itemManagementRepository);
            case CHANGERATINGFEEDBACK:
                return new ChangeRatingFeedback(itemManagementRepository);
            case SHOWMEMBERACTIVITY:
                return new ShowMemberActivity(itemManagementRepository);
            case CHANGESTATUSFEEDBACK:
                return new ChangeStatusFeedback(itemManagementRepository);
            case SHOWTEAMALLMEMBERS:
                return new ShowAllTeamMembers(itemManagementRepository);
            case SHOWTEAMACTIVITY:
                return new ShowTeamActivity(itemManagementRepository);
            case LISTWORKITEMS:
                return new ListWorkItems(itemManagementRepository);
            case FILTERITEMS:
                return new FilterItems(itemManagementRepository);
            case FILTERBYSTATUS:
                return new FilterByStatus(itemManagementRepository);
            case FILTERBYASIGNEE:
                return new FilterByAsignee(itemManagementRepository);
            case DELETEMEMBER:
                return new DeleteMember(itemManagementRepository);
            case REMOVEMEMBERFROMTEAM:
                return new RemoveMemberFromTeam(itemManagementRepository);
            case DELETETEAM:
                return new DeleteTeam(itemManagementRepository);
            case DELETEBOARD:
                return new DeleteBoard(itemManagementRepository);
            case DELETEWORKITEM:
                return new DeleteWorkItem(itemManagementRepository);
            case SORT:
                return new Sort(itemManagementRepository);
            default:
                throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandName));

        }

    }
}
