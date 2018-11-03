package ren.lingpai.lppoint.model;

import ren.lingpai.lppoint.entity.MessageDO;

import java.util.List;

/**
 * Created by lrp on 17-5-15.
 */
public class MessageBO extends MessageDO{

    private String picPath;
    private String username;
    private String realName;

    private List<MessageBO> subMessages;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<MessageBO> getSubMessages() {
        return subMessages;
    }

    public void setSubMessages(List<MessageBO> subMessages) {
        this.subMessages = subMessages;
    }
}
