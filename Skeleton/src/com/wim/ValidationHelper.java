package com.wim;

import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Bug;
import com.wim.models.contracts.Feedback;
import com.wim.models.contracts.Story;
import com.wim.models.contracts.WorkItem;
import com.wim.models.implementation.WorkItemImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationHelper {
    public static void validateIntRange(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateArgumentIsNotNull(Object arg, String message) {
        if (arg == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNegative(int num, String message) {
        if (num < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <E> void isListEmpty(List<E> list, String error, String argument) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException(String.format(error, argument));
        }
    }

    public static <E> void validateIsPresent(List<E> list, E item, String error) {
        if (item instanceof Bug) {
            List<Bug> bugList = new ArrayList<>();
            for (E e : list) {
                if (e instanceof Bug) {
                    bugList.add((Bug) e);
                }
            }
            Bug castedItem = (Bug) item;
            for (Bug bug : bugList) {
                if (bug.getTitle().equals(castedItem.getTitle())) {
                    WorkItemImpl.counter--;
                    throw new IllegalArgumentException(error);
                }
            }
        } else if (item instanceof Story) {
            List<Story> storyList = new ArrayList<>();
            for (E e : list) {
                if (e instanceof Story) {
                    storyList.add((Story) e);
                }
            }
            Story castedItem = (Story) item;
            for (Story story : storyList) {
                if (story.getTitle().equals(castedItem.getTitle())) {
                    WorkItemImpl.counter--;
                    throw new IllegalArgumentException(error);
                }
            }
        } else if (item instanceof Feedback) {
            List<Feedback> feedbackList = new ArrayList<>();
            for (E e : list) {
                if (e instanceof Feedback) {
                    feedbackList.add((Feedback) e);
                }
            }
            Feedback castedItem = (Feedback) item;
            for (Feedback feedback : feedbackList) {
                if (feedback.getTitle().equals(castedItem.getTitle())) {
                    WorkItemImpl.counter--;
                    throw new IllegalArgumentException(error);
                }
            }
        }
    }

    public static <E, T> void validateIsPresent(Map<E, T> map, T item, String error) {
        for (T value : map.values()) {
            if (value.equals(item)) {
                throw new IllegalArgumentException(error);
            }
        }
    }

}
