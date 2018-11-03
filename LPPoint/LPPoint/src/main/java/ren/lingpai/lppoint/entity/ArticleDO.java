package ren.lingpai.lppoint.entity;

import java.sql.Date;
import java.util.Arrays;

/**
 * Created by lrp on 17-3-5.
 */
public class ArticleDO {

    //主键
    private int id;
    //标题
    private String title;
    //作者
    private int creator;
    //创建时间
    private Date createTime;
    //摘要
    private String summary;
    //封面图片
    private byte[] coverImg;
    //内容
    private byte[] articleContent;
    //阅读量
    private int readNum;
    //点赞量
    private int upNum;
    //排序
    private int sort;

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

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public byte[] getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(byte[] coverImg) {
        this.coverImg = coverImg;
    }

    public byte[] getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(byte[] articleContent) {
        this.articleContent = articleContent;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getUpNum() {
        return upNum;
    }

    public void setUpNum(int upNum) {
        this.upNum = upNum;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ArticleDO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", summary='" + summary + '\'' +
                ", coverImg=" + Arrays.toString(coverImg) +
                ", articleContent=" + Arrays.toString(articleContent) +
                ", readNum=" + readNum +
                ", upNum=" + upNum +
                ", sort=" + sort +
                '}';
    }
}
