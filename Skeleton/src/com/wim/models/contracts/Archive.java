package com.wim.models.contracts;

import java.util.Map;

public interface Archive {

    Map<String, WorkItem> getWorkItemsArchive();

    Map<String, Board> getBoardArchive();

    Map<String, Member> getMemberArchive ();

    Map<String, Team> getTeamArchive ();
}
