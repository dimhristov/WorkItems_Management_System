package com.wim.core.contracts;

import com.wim.models.contracts.Archive;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ItemManagementRepository {

    Archive getArchive();

    void addMemberToArchive (Member member);

    void addTeamToArchive (Team team);

    Map<String, Member> getMembers ();

    Map<String, Team> getTeams ();

    void addMember (String name, Member member);

    void addTeam (String name, Team team);

    Team getTeam (String teamName);

    Member getMember (String name);

     Board findBoard (String boardName, String teamName);
}
