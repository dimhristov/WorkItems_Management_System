package com.wim.models.contracts;

import java.util.List;

public interface Team {

    String getName();

    List<Member> getMemberList();

    Member getMember(String name);

    List<Board> getBoardList();

    void addMemberToTeam(Member member);

    Board getBoard(String name);

    void removeMemberFromTeam(Member member);

    String showAllTeamMembers();

    void addBoard(Board board);

    void removeBoard(Board board);
}
