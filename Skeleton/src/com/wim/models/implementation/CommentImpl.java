package com.wim.models.implementation;


import com.wim.models.contracts.Comment;
import com.wim.models.contracts.Member;

import static com.wim.ValidationHelper.validateArgumentIsNotNull;
import static com.wim.ValidationHelper.validateIntRange;
import static com.wim.models.common.constants.Constants.*;
;

public class CommentImpl implements Comment {

    private Member author;
    private String description;

    public CommentImpl(Member author, String description) {
        setAuthor(author);
        setDescription(description);
    }

    private void setAuthor(Member author) {
        validateArgumentIsNotNull(author, AUTHOR_NULL_ERR_MESS);
        this.author = author;
    }

    private void setDescription(String description) {
        validateIntRange(description.length(), DESCRIPTION_MIN_NAME_LEN, DESCRIPTION_MAX_NAME_LEN, DESCRIPTION_ERR_MESS);
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Member getAuthor() {
        return this.author;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("▆ Comment");
        sb.append(System.lineSeparator());
        sb.append(String.format("Author: %s", author.getName()));
        sb.append(System.lineSeparator());
        sb.append("▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔");
        sb.append(System.lineSeparator());
        sb.append(description);
        return sb.toString();
    }

}
