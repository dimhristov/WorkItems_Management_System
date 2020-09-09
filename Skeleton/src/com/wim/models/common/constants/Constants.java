package com.wim.models.common.constants;

public class Constants {

    public static final int MEMBER_MIN_NAME_LEN = 5;
    public static final int MEMBER_MAX_NAME_LEN = 15;
    public static final String MEMBER_ERR_MESS = String.format("Member name must be between %d and %d"
            , MEMBER_MIN_NAME_LEN, MEMBER_MAX_NAME_LEN);

    public static final int BOARD_MIN_NAME_LEN = 5;
    public static final int BOARD_MAX_NAME_LEN = 10;
    public static final String BOARD_ERR_MESS = String.format("Board name must be between %d and %d"
            , BOARD_MIN_NAME_LEN, BOARD_MAX_NAME_LEN);

    public static final int TITLE_MIN_NAME_LEN = 10;
    public static final int TITLE_MAX_NAME_LEN = 50;
    public static final String TITLE_NULL_ERR_MESS = "Title is null";
    public static final String TITLE_ERR_MESS = String.format("Title length must be between %d and %d "
            , TITLE_MIN_NAME_LEN, TITLE_MAX_NAME_LEN);

    public static final int DESCRIPTION_MIN_NAME_LEN = 10;
    public static final int DESCRIPTION_MAX_NAME_LEN = 500;
    public static final String DESCRIPTION_ERR_MESS = String.format("Description must be between %d and %d"
            , DESCRIPTION_MIN_NAME_LEN, DESCRIPTION_MAX_NAME_LEN);

    public static final int TEAM_MIN_NAME_LEN = 5;
    public static final int TEAM_MAX_NAME_LEN = 15;
    public static final String TEAM_ERR_MESS = String.format("Team name must be between %d and %d"
            , TEAM_MIN_NAME_LEN, TEAM_MAX_NAME_LEN);


    public static final String ASSIGNEE_ERR_MESS = "Member assignee can't be null.";

    public static final String RATING_ERR_MESS = "Rating can't be less than 0.";

    public static final String EMPTY_LIST_ERR_MESS = "%s List is empty!";

    public static final String NO_EXISTING_PEOPLE = "There are no existing members!";

    public static final String TEAM_NOT_EXISTS_ERROR_MESSAGE = "Team with name %s doesn't exist!";

    public static final String NO_BOARDS_IN_TEAM = "There are no boards in team %s";

    public static final String AUTHOR_NULL_ERR_MESS = "Author can't be null!";

    public static final String MEMBER_NAME_NULL_ERR_MESS = "Member name can't be null";

    public static final String THERE_IS_NOT_ANY_ACTIVITY = "There is not any activity.";

    public static final String PERSON_NOT_EXISTS_ERROR_MESSAGE = "Person with name %s doesn't exist!";

    public static final String BOARD_NOT_EXISTS_ERROR_MESSAGE ="Board with name %s doesn't exist!";

}


