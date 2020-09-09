package com.wim.core.factories;

import com.wim.commands.show.ShowAllMembers;
import com.wim.core.contracts.ItemManagementFactory;
import com.wim.models.common.enums.PriorityType;
import com.wim.models.common.enums.StorySizeType;
import com.wim.models.common.enums.StoryStatusType;
import com.wim.models.contracts.*;
import com.wim.models.implementation.*;

import java.util.List;

public class ItemManagementFactoryImpl implements ItemManagementFactory {


    @Override
    public Team createTeam(String name) {
        return new TeamImpl(name);
    }

    @Override
    public Member createMember(String name) {
        return new MemberImpl(name);
    }

    @Override
    public Board createBoard(String name) {
        return new BoardImpl(name);
    }

    @Override
    public Bug createBug(String title, String description, String priority, String severity, List<String> stepsToReproduce) {
        return new BugImpl(title, description, priority, severity,stepsToReproduce);
    }

    @Override
    public Story createStory(String title, String description, String storySizeType, String priority) {
        return new StoryImpl(title, description, storySizeType,priority);
    }

    @Override
    public Feedback createFeedback(String title, String description, int rating) {
        return new FeedbackImpl(title, description, rating);
    }


}
