package com.wim.models.implementation;

import com.wim.models.contracts.*;

import java.util.HashMap;
import java.util.Map;

public class ArchiveImpl implements Archive {

    private Map<String, Member> memberArchive;
    private Map<String, Team> teamArchive;
    private Map<String, WorkItem> workItemsArchive;
    private Map<String, Board> boardArchive;

    public ArchiveImpl() {
        this.memberArchive = new HashMap<>();
        this.teamArchive = new HashMap<>();
        this.workItemsArchive = new HashMap<>();
        this.boardArchive = new HashMap<>();
    }

    @Override
    public Map<String, WorkItem> getWorkItemsArchive() {
        return this.workItemsArchive;
    }

    @Override
    public Map<String, Board> getBoardArchive() {
        return this.boardArchive;
    }

    @Override
    public Map<String, Member> getMemberArchive() {
        return memberArchive;
    }

    @Override
    public Map<String, Team> getTeamArchive() {
        return teamArchive;
    }
}
