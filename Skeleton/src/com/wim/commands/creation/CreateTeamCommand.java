package com.wim.commands.creation;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementFactory;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Team;

import java.util.List;

public class CreateTeamCommand extends CommandImpl implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse CreateTeam command parameters.";
    private static final String EXECUTION_MESS = "Team with name %s has been created!";

    private final ItemManagementFactory factory;
    private final ItemManagementRepository repository;

    private String name;

    public CreateTeamCommand (ItemManagementFactory factory, ItemManagementRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    @Override
    public String execute (List<String> parameters) {
        validateInput(parameters,EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        createTeam(name);
        return executionMessage(name);
    }

    private void parseParameters (List<String> parameters) {
        try {
            name = parameters.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    private void createTeam(String name) {
        Team team = factory.createTeam(name);
        repository.addTeam(name, team);
    }

    private String executionMessage(String name) {
        return String.format(EXECUTION_MESS, repository.getTeam(name).getName());
    }

}
