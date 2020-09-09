package com.wim.commands.show;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Team;

import java.util.ArrayList;
import java.util.List;

import static com.wim.models.common.constants.Constants.NO_EXISTING_PEOPLE;

public class ShowAllTeams extends CommandImpl implements Command {

      private final ItemManagementRepository itemRepository;

    public ShowAllTeams(ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public String execute(List<String> parameters) {

        if (itemRepository.getTeams().size() == 0) {
            throw new IllegalArgumentException(NO_EXISTING_PEOPLE);
        }

        List<Team> allTeams = new ArrayList<>(itemRepository.getTeams().values());
        String removedCommas = allTeams.toString().replaceAll(",[\\s]*", "");
        return removedCommas.replaceAll("[\\[\\]]","");
    }

}
