package com.wim.commands.list;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Bug;
import com.wim.models.contracts.Story;
import com.wim.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterByAsignee extends CommandImpl implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String FAILED_TO_PARSE_ERR_MESS = "Failed to parse FilterByAsignee command parameters.";
    private static final String MEMBER_DOESNT_HAVE_ASSIGNEES = "Member with name %s doesn't have any assigned items.";

    private final ItemManagementRepository itemRepository;
    private String asigneeName;

    public FilterByAsignee(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return filterByAsignee(asigneeName);
    }

    private void parseParameters(List<String> parameters) {
        try {
            asigneeName = parameters.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_TO_PARSE_ERR_MESS);
        }
    }

    private String filterByAsignee(String asigneeName) {
        String name = asigneeName;
        String message = String.format(MEMBER_DOESNT_HAVE_ASSIGNEES, name);
        String listOfItems;

        StringBuilder sb = new StringBuilder();
        List<WorkItem> bugAndStoryList = new ArrayList<>();

        List<WorkItem> storyList = itemRepository.getTeams().values().stream()
                .flatMap(t -> t.getBoardList().stream())
                .flatMap(b -> b.getWorkItems().stream())
                .filter(i -> i instanceof Story)
                .collect(Collectors.toList());

        List<WorkItem> bugList = itemRepository.getTeams().values().stream()
                .flatMap(t -> t.getBoardList().stream())
                .flatMap(b -> b.getWorkItems().stream())
                .filter(i -> i instanceof Bug)
                .collect(Collectors.toList());

        bugAndStoryList.addAll(storyList);
        bugAndStoryList.addAll(bugList);

        for (WorkItem workItem : bugAndStoryList) {
            try {
                if (workItem.getAsignee(name).equalsIgnoreCase(name)) {
                    sb.append(workItem).append(System.lineSeparator());
                }
            } catch (IllegalArgumentException e) {
            }
        }
        listOfItems = sb.toString();
        return !listOfItems.isEmpty() ? listOfItems : message;
    }
}
