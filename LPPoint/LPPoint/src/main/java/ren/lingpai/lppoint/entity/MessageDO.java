package ren.lingpai.lppoint.entity;

import java.sql.Date;

/**
 * Created by lrp on 17-5-15.
 */
public class MessageDO {

    private int id;
    private int userId;
    private String content;
    private Date createTime;
    private int pMsgId;
    private int level;
    private int articleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPMsgId() {
        return pMsgId;
    }

    public void setPMsgId(int pMsgId) {
        this.pMsgId = pMsgId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
