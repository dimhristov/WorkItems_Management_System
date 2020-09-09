package com.wim.core.providers;

import com.wim.core.contracts.CommandParser;

import java.util.ArrayList;
import java.util.List;

public class CommandParserImpl implements CommandParser {
    // To Check again
    public String parseCommand(String fullCommand) {
        return fullCommand.split(" ")[0];
    }

    public List<String> parseParameters(String fullCommand) {
        String[] commandParts = fullCommand.split(" ");
        ArrayList<String> parameters = new ArrayList<>();
        for (int i = 1; i < commandParts.length; i++) {
            parameters.add(commandParts[i]);
        }
        return parameters;
    }
}
