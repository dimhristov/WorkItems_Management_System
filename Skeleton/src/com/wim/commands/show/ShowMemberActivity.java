package com.wim.commands.show;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;

import java.util.List;

public class ShowMemberActivity extends CommandImpl implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse showMemberActivity command parameters.";

    private final ItemManagementRepository itemRepository;
    private String personName;

    public ShowMemberActivity(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return showMemberActivity(personName);
    }

    private void parseParameters(List<String> parameters) {
        try {
            personName = parameters.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private String showMemberActivity(String personName) {
        if (!itemRepository.getMembers().containsKey(personName)) {
            throw new IllegalArgumentException(String.format("Member %s doesn't exist", personName));
        }
        return itemRepository.getMember(personName).showActivity();
    }
}
