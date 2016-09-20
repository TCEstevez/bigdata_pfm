package pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Question implements Serializable{

    private static final long serialVersionUID = -569856422578965415L;

    Long id;
    int postTypeId;
    Long parentId;

    Date creationDate;
    Long score;
    Long ownerUserId;
    Date lastActivityDate;
    Long viewCount;
    String body;
    Long favoriteCount;
    Long answerCount;
    Long commentCount;
    Long acceptedAnswerId;
    ArrayList<String> tags;
    String gradoDificultad1;

    public Question() {
    }

    public Question(Long id, int postTypeId, Long parentId, Date creationDate, Long score, Long ownerUserId, Date lastActivityDate, Long viewCount, String body, Long favoriteCount, Long answerCount, Long commentCount, Long acceptedAnswerId, ArrayList<String> tags) {
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
        this.tags = tags;
        this.gradoDificultad1="";
    }

    public String getGradoDificultad1() {
        return gradoDificultad1;
    }

    public void setGradoDificultad1(String gradoDificultad1) {
        this.gradoDificultad1 = gradoDificultad1;
    }

    public Long getId() {
        return id;
    }

    public String getIdString() {
        return String.valueOf(this.id);
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

    public String getParentIdString() {
        return String.valueOf(this.parentId);
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getCreationDateString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return df.format(this.creationDate);
    }

    public void setCreationDate(Date creationDate) {
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

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public String getLastActivityDateString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return df.format(this.lastActivityDate);
    }

    public void setLastActivityDate(Date lastActivityDate) {
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

    public Long getAcceptedAnswerId() {
        return acceptedAnswerId;
    }

    public String getAcceptedAnswerIdString() {
        return String.valueOf(acceptedAnswerId);
    }

    public void setAcceptedAnswerId(Long acceptedAnswerId) {
        this.acceptedAnswerId = acceptedAnswerId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", postTypeId=" + postTypeId +
                ", parentId=" + parentId +
                ", creationDate=" + creationDate +
                ", score=" + score +
                ", ownerUserId=" + ownerUserId +
                ", lastActivityDate=" + lastActivityDate +
                ", viewCount=" + viewCount +
                ", body='" + body + '\'' +
                ", favoriteCount=" + favoriteCount +
                ", answerCount=" + answerCount +
                ", commentCount=" + commentCount +
                ", acceptedAnswerId=" + acceptedAnswerId +
                ", tags=" + tags +
                '}';
    }

    public String toJson(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
            return mapper.writeValueAsString(this);
       } catch (JsonProcessingException e) {
           return "";
        }

    }
}

