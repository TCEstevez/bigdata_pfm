package com.spring.elasticsearch.model.entities;

import org.springframework.data.elasticsearch.annotations.*;

import java.util.ArrayList;

@Document(indexName = "posts", type = "row")
public class Posts {
    Long id;

    int postTypeId;
    Long parentId;

    String creationDate;
    Long score;
    Long ownerUserId;

    String lastActivityDate;
    Long viewCount;
    String body;
    Long favoriteCount;
    Long answerCount;
    Long commentCount;
    Long acceptedAnswerId;
    double gradoDificultad1;
    String gradoDificultad2;
    @Field( type = FieldType.Nested)
    ArrayList<String> tags;



    public Posts() {
    }

    public Posts(Long id, int postTypeId, Long parentId, String creationDate, Long score, Long ownerUserId, String lastActivityDate, Long viewCount, String body, Long favoriteCount, Long answerCount, Long commentCount, Long acceptedAnswerId, double gradoDificultad1, String gradoDificultad2, ArrayList<String> tags) {
        this.id = id;
        this.postTypeId = postTypeId;
        this.parentId = parentId;
        this.creationDate = creationDate;
        this.score = score;
        this.ownerUserId = ownerUserId;
        this.lastActivityDate = lastActivityDate;
        this.viewCount = viewCount;
        this.body = body;
        this.favoriteCount = favoriteCount;
        this.answerCount = answerCount;
        this.commentCount = commentCount;
        this.acceptedAnswerId = acceptedAnswerId;
        this.gradoDificultad1 = gradoDificultad1;
        this.gradoDificultad2 = gradoDificultad2;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(int postTypeId) {
        this.postTypeId = postTypeId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Long getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Long answerCount) {
        this.answerCount = answerCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public double getGradoDificultad1() {
        return gradoDificultad1;
    }

    public void setGradoDificultad1(double gradoDificultad1) {
        this.gradoDificultad1 = gradoDificultad1;
    }

    public String getGradoDificultad2() {
        return gradoDificultad2;
    }

    public void setGradoDificultad2(String gradoDificultad2) {
        this.gradoDificultad2 = gradoDificultad2;
    }

    public Long getAcceptedAnswerId() {
        return acceptedAnswerId;
    }

    public void setAcceptedAnswerId(Long acceptedAnswerId) {
        this.acceptedAnswerId = acceptedAnswerId;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", postTypeId=" + postTypeId +
                ", parentId=" + parentId +
                ", creationDate='" + creationDate + '\'' +
                ", score=" + score +
                ", ownerUserId=" + ownerUserId +
                ", lastActivityDate='" + lastActivityDate + '\'' +
                ", viewCount=" + viewCount +
               // ", body='" + body + '\'' +
                ", favoriteCount=" + favoriteCount +
                ", answerCount=" + answerCount +
                ", commentCount=" + commentCount +
                ", acceptedAnswerId=" + acceptedAnswerId +
                ", gradoDificultad1='" + gradoDificultad1 + '\'' +
                ", gradoDificultad2='" + gradoDificultad2 + '\'' +
                ", tags=" + tags +
                '}';
    }
}

