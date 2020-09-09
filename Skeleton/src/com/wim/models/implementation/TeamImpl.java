package com.wim.models.implementation;


import com.wim.ValidationHelper;
import com.wim.core.ItemManagementRepositoryImpl;
import com.wim.models.contracts.Board;
import com.wim.models.contracts.Member;
import com.wim.models.contracts.Team;

import java.util.ArrayList;
import java.util.List;

import static com.wim.ValidationHelper.*;
import static com.wim.models.common.constants.Constants.*;

public class TeamImpl implements Team {

    private static final String BOARD_NULL_ERR_MESS = "Board can't be null";
    private static final String TEAM_NULL_ERR_MESS = "Team name can't be null";
    private static final String MEMBER_NOT_PART_OF_TEAM_MESS = "Member with name %s is not part of team %s";
    private static final String MEMBER_ALREADY_PART_OF_TEAM_MESS = "Member with this name is already part of the team.";
    private static final String BOARD_DOESNT_EXIST = "Board with name %s doesn't exist";

    private String name;
    private List<Member> memberList;
    private List<Board> boardList;

    public TeamImpl(String name) {
        setName(name);
        this.memberList = new ArrayList<>();
        this.boardList = new ArrayList<>();
    }

    private void setName(String name) {
        validateArgumentIsNotNull(name, TEAM_NULL_ERR_MESS);
        validateIntRange(name.length(), TEAM_MIN_NAME_LEN, TEAM_MAX_NAME_LEN, TEAM_ERR_MESS);
        this.name = name;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Member> getMemberList() {
        return this.memberList;
    }
    @Override
    public Member getMember(String name) {
        for (Member member : memberList) {
            if (member.getName().equals(name)) {
                return member;
            }
        }
        throw new IllegalArgumentException(String.format(MEMBER_NOT_PART_OF_TEAM_MESS,name,this.name));
    }

    @Override
    public List<Board> getBoardList() {
        return this.boardList;
    }


    @Override
    public void addMemberToTeam(Member member) {
        if (memberList.contains(member)) {
            throw new IllegalArgumentException(MEMBER_ALREADY_PART_OF_TEAM_MESS);
        }
        memberList.add(member);
    }
    @Override
    public Board getBoard(String name) {
        for (Board board : boardList) {
            if (board.getName().equals(name)) {
                return board;
            }
        }
        throw new IllegalArgumentException(String.format(BOARD_DOESNT_EXIST, name));
    }

    @Override
    public void removeMemberFromTeam(Member member) {
        memberList.remove(member);
    }

    @Override
    public void addBoard(Board board) {
        validateArgumentIsNotNull(board, BOARD_NULL_ERR_MESS);
        for (Board value : boardList) {
            if (value.getName().equals(board.getName())) {
                throw new IllegalArgumentException("Can't exist boards with same names!");
            }
        }
        boardList.add(board);
    }

    @Override
    public void removeBoard(Board board) {
        validateArgumentIsNotNull(board, BOARD_NULL_ERR_MESS);
        boardList.remove(board);
    }

    @Override
    public String showAllTeamMembers() {
        StringBuilder sb = new StringBuilder();
        sb.append("     ≣ List of all Team Members");
        sb.append(System.lineSeparator());
        if (memberList.size() == 0) {
            sb.append("       This team doesn't have any members in it");
            sb.append(System.lineSeparator());
        } else {
            for (Member member : memberList) {
                sb.append(member.toString());
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("▆▆▆▆▆ Team: %s ", this.getName()));
        sb.append(System.lineSeparator());
        sb.append(showAllTeamMembers());
        return sb.toString();
    }
}
