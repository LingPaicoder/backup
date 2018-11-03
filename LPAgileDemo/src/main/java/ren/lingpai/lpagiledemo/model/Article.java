package ren.lingpai.lpagiledemo.model;

/**
 * 文章类
 * Created by lrp on 17-3-5.
 */
public class Article {

    private int id;         //主键
    private String title;   //文章
    private String summary; //摘要
    private String content; //内容

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
