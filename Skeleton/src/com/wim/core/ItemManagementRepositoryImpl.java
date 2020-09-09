package com.wim.core;

import com.wim.core.contracts.ItemManagementRepository;
import com.wim.models.contracts.Archive;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;
import com.wim.models.implementation.ArchiveImpl;

import java.util.*;

public class ItemManagementRepositoryImpl implements ItemManagementRepository {

    private final Map<String, Member> members;
    private final Map<String, Team> teams;
    private final Archive archive;

    public ItemManagementRepositoryImpl() {
        this.members = new HashMap<>();
        this.teams = new HashMap<>();
        this.archive = new ArchiveImpl();
    }

    @Override
    public Archive getArchive() {
        return this.archive;
    }

    @Override
    public void addMemberToArchive(Member member) {
        archive.getMemberArchive().put(member.getName(), member);
    }

    @Override
    public void addTeamToArchive(Team team) {
        archive.getTeamArchive().put(team.getName(), team);
    }

    @Override
    public Map<String, Member> getMembers() {
        return this.members;
    }

    @Override
    public Map<String, Team> getTeams() {
        return teams;
    }

    @Override
    public void addMember(String name, Member member) {
        if (!members.containsKey(name)) {
            this.members.put(name, member);
        } else {
            throw new IllegalArgumentException(String.format("Member with name %s already exist!", name));
        }
    }

    @Override
    public void addTeam(String name, Team team) {
        if (!teams.containsKey(name)) {
            this.teams.put(name, team);
        } else {
            throw new IllegalArgumentException(String.format("Team with name %s already exist!", name));
        }
        this.teams.put(name, team);
    }

    @Override
    public Team getTeam(String teamName) {
        if (teams.containsKey(teamName)) {
            return teams.get(teamName);
        } else {
            throw new IllegalArgumentException(String.format("Team with name %s doesn't exist!", teamName));
        }
    }

    @Override
    public Member getMember(String name) {
        if (members.containsKey(name)) {
            return members.get(name);
        } else {
            throw new IllegalArgumentException(String.format("Person with name %s doesn't exist!", name));
        }
    }

    @Override
    public Board findBoard(String boardName, String teamName) {
        Team team = getTeam(teamName);
        for (Board board : team.getBoardList()) {
            if (board.getName().equals((boardName))) {
                return board;
            }
        }
        throw new IllegalArgumentException(String.format("Board with name %s doesn't exist!", boardName));
    }
}
