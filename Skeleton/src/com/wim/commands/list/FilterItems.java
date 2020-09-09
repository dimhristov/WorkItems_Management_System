package com.wim.commands.list;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.WorkItemType;
import com.wim.models.contracts.*;

import java.util.List;

public class FilterItems extends CommandImpl implements Command {

    private static final String INVALID_COMMAND_ERR_MESS = "Please enter a valid command. Must be either [story] [bug] [feedback]";
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse FilterWorkItems command parameters.";
    private static final String NO_EXISTING_ITEMS_MESS = "There are no existing items.";

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final ItemManagementRepository itemRepository;
    private String filterType;

    public FilterItems(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return filterWorkItems(parameters);
    }

    private void parseParameters(List<String> parameters) {
        try {
            filterType = parameters.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private String filterWorkItems(List<String> parameters) {
        String filter = parameters.get(0);
        String message = NO_EXISTING_ITEMS_MESS;
        StringBuilder sb;
        String listOfItems;

        switch (filter.toLowerCase()) {
            case "bug":
                sb = new StringBuilder();
                itemRepository.getTeams().values().stream()
                        .flatMap(t -> t.getBoardList().stream())
                        .flatMap(b -> b.getWorkItems().stream())
                        .filter(i -> i instanceof Bug)
                        .forEach(i -> {
                            sb.append(i);
                            sb.append(System.lineSeparator());
                        });
                listOfItems = sb.toString();
                break;

            case "story":
                sb = new StringBuilder();
                itemRepository.getTeams().values().stream()
                        .flatMap(t -> t.getBoardList().stream())
                        .flatMap(b -> b.getWorkItems().stream())
                        .filter(i -> i instanceof Story)
                        .forEach(i -> {
                            sb.append(i);
                            sb.append(System.lineSeparator());
                        });

                listOfItems = sb.toString();
                break;
            case "feedback":
                sb = new StringBuilder();
                itemRepository.getTeams().values().stream()
                        .flatMap(t -> t.getBoardList().stream())
                        .flatMap(b -> b.getWorkItems().stream())
                        .filter(i -> i instanceof Feedback)
                        .forEach(i -> {
                            sb.append(i);
                            sb.append(System.lineSeparator());
                        });

                listOfItems = sb.toString();
                break;
            default:
                throw new IllegalArgumentException(INVALID_COMMAND_ERR_MESS);
        }
        return !listOfItems.isEmpty() ? listOfItems : message;
    }
}
