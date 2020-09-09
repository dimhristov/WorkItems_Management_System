package com.wim.commands.list;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Team;

import java.util.List;

public class ListWorkItems extends CommandImpl implements Command {

    private static final String NO_EXISTING_ITEMS_ERR_MESS = "The are no work items";

    private final ItemManagementRepository itemRepository;

    public ListWorkItems(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        StringBuilder sb = new StringBuilder();
        itemRepository.getTeams().values().stream()
                .flatMap(t -> t.getBoardList().stream())
                .flatMap(b -> b.getWorkItems().stream())
                .forEach(i -> {
                    sb.append(i).append(System.lineSeparator());
                });

        if (sb.toString().isEmpty()) {
            return NO_EXISTING_ITEMS_ERR_MESS;
        } else {
            return sb.toString();
        }
    }
}
