package com.wim.commands.show;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Member;

import static com.wim.models.common.constants.Constants.NO_EXISTING_PEOPLE;
import java.util.ArrayList;
import java.util.List;


public class ShowAllMembers extends CommandImpl implements Command {

    private final ItemManagementRepository itemRepository;

    public ShowAllMembers(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String execute(List<String> parameters) {

        if (itemRepository.getMembers().size() == 0) {
            throw new IllegalArgumentException(NO_EXISTING_PEOPLE);
        }

        List<Member> allMembers = new ArrayList<>(itemRepository.getMembers().values());
        String removedCommas = allMembers.toString().replaceAll(",[\\s]*", "");
        return removedCommas.replaceAll("[\\[\\]]","");
    }
}
