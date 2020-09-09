package com.wim.commands;

import com.wim.commands.contracts.Command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.wim.commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;

public abstract class CommandImpl implements Command {



    protected List<String> removeDescriptionFromList(List<String> parameters) {
        String fullCommand = parameters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

          String newCommand = fullCommand.replaceAll("\\[.+]", "");
        return Arrays.stream(newCommand.split("\\s+")).collect(Collectors.toList());
    }

    protected String separateDescriptionFromList(List<String> parameters) {
        String fullCommand = parameters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        int beginIndex = fullCommand.indexOf("[");
        int endIndex = fullCommand.indexOf("]");
        return fullCommand.substring(beginIndex+1, endIndex);
    }

    protected void validateInput(List<String> parameters, int EXPECTED_NUMBER_OF_ARGUMENTS ) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(
                    INVALID_NUMBER_OF_ARGUMENTS,
                    EXPECTED_NUMBER_OF_ARGUMENTS,
                    parameters.size()));
        }
    }

    protected String printListActivity(List<String> activity) {
        StringBuilder sb = new StringBuilder();

        activity.forEach(e -> {
            sb.append(" ⤤ ");
            sb.append(e);
            sb.append(System.lineSeparator());

        });
        return sb.toString();
    }



}
