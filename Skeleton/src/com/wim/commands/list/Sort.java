package com.wim.commands.list;

import com.wim.commands.CommandImpl;
import com.wim.commands.contracts.Command;
import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Bug;
import com.wim.models.contracts.Feedback;
import com.wim.models.contracts.Story;
import com.wim.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sort extends CommandImpl implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String WRONG_COMMAND_ERR_MESS = "Insert correct command, you can choose between " +
            "Title, Priority, Severity, Size, Rating.";
    private static final String FAILED_PARSE_ERR_MESS = "Failed to parse Sort command parameters.";

    private final ItemManagementRepository itemRepository;
    private String filterType;

    public Sort (ItemManagementRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public String execute (List<String> parameters) {
        validateInput(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);

        switch (filterType) {
            case "Title":
                return sortItemsByTitle();
            case "Priority":
                return sortItemsByPriority();
            case "Severity":
                return sortItemsBySeverity();
            case "Size":
                return sortItemsBySize();
            case "Rating":
                return sortItemsByRating();
        }
        throw new IllegalArgumentException (WRONG_COMMAND_ERR_MESS);
    }

    private void parseParameters (List<String> parameters) {
        try {
            filterType = parameters.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(FAILED_PARSE_ERR_MESS);
        }
    }

    String message = "There are no existing items.";
    String listOfItems;
    StringBuilder sb = new StringBuilder();

    private String sortItemsByTitle () {
        itemRepository.getTeams().values().stream()
                .flatMap(t -> t.getBoardList().stream())
                .flatMap(b -> b.getWorkItems().stream())
                .sorted(Comparator.comparing(WorkItem::getTitle))
                .forEach(i -> {
                    sb.append(i);
                    sb.append(System.lineSeparator());
                });
        listOfItems = sb.toString();
        return !listOfItems.isEmpty() ? listOfItems : message;
    }

    private String sortItemsByPriority () {
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

        bugAndStoryList.stream()
                .sorted(Comparator.comparingInt(item -> item.getPriorityType().getIndex()))
                .forEach(i -> {
                    sb.append(i);
                    sb.append(System.lineSeparator());
                });
        String listOfItems = sb.toString();
        return !listOfItems.isEmpty() ? listOfItems : message;


    }

    private String sortItemsBySeverity () {
        List<Bug> bugList = itemRepository.getTeams().values().stream()
                .flatMap(t -> t.getBoardList().stream())
                .flatMap(b -> b.getWorkItems().stream())
                .filter(i -> i instanceof Bug)
                .map(i -> (Bug) i)
                .collect(Collectors.toList());

        bugList.stream()
                .sorted(Comparator.comparingInt(item -> item.getSeverity().getIndex()))
                .forEach(i -> {
                    sb.append(i);
                    sb.append(System.lineSeparator());
                });
        listOfItems = sb.toString();
        return !listOfItems.isEmpty() ? listOfItems : message;
    }

    private String sortItemsBySize () {
        List<Story> storyList = itemRepository.getTeams().values().stream()
                .flatMap(t -> t.getBoardList().stream())
                .flatMap(b -> b.getWorkItems().stream())
                .filter(i -> i instanceof Story)
                .map(i -> (Story) i)
                .collect(Collectors.toList());

        storyList.stream()
                .sorted(Comparator.comparing(item -> item.getSize().getIndex()))
                .forEach(i -> {
                    sb.append(i);
                    sb.append(System.lineSeparator());
                });
        listOfItems = sb.toString();
        return !listOfItems.isEmpty() ? listOfItems : message;
    }

    private String sortItemsByRating () {
        List<Feedback> feedbackList = itemRepository.getTeams().values().stream()
                .flatMap(t -> t.getBoardList().stream())
                .flatMap(b -> b.getWorkItems().stream())
                .filter(i -> i instanceof Feedback)
                .map(i -> (Feedback) i)
                .collect(Collectors.toList());

        feedbackList.stream()
                .sorted(Comparator.comparingInt(Feedback::getRating))
                .forEach(i -> {
                    sb.append(i);
                    sb.append(System.lineSeparator());
                });
        listOfItems = sb.toString();
        return !listOfItems.isEmpty() ? listOfItems : message;
    }
}
