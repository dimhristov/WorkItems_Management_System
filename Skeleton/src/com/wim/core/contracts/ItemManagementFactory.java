package com.wim.core.contracts;

import com.wim.models.contracts.*;

import java.util.List;

public interface ItemManagementFactory {

    Team createTeam(String name);

    Member createMember(String name);

    Board createBoard(String name);

    Bug createBug(String title, String description, String priority, String severity, List<String> stepsToReproduce);

    Story createStory(String title, String description, String storySizeType, String priority);

    Feedback createFeedback(String title, String description, int rating);
}
