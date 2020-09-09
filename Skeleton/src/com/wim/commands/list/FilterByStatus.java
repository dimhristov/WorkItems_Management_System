package com.wim.commands.list;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.common.enums.WorkItemsStatusTypes;
import com.wim.models.contracts.*;

import java.util.List;

public class FilterByStatus extends CommandImpl implements Command {

    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse FilterByStatus command parameters.";
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private final ItemManagementRepository itemRepository;
    private String filterType;

    public FilterByStatus(ItemManagementRepository itemRepository) {
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
        String filter = parameters.get(0).toUpperCase();
        String message = "There are no existing items.";
        String listOfItems;

        StringBuilder sb = new StringBuilder();
                itemRepository.getTeams().values().stream()
                        .flatMap(t -> t.getBoardList().stream())
                        .flatMap(b -> b.getWorkItems().stream())
                        .filter(i -> i.getItemsStatus().toUpperCase().equalsIgnoreCase(filter))
                        .forEach(i -> {
                            sb.append(i);
                            sb.append(System.lineSeparator());
                        });
                listOfItems = sb.toString();

        return !listOfItems.isEmpty() ? listOfItems : message;
    }

}
