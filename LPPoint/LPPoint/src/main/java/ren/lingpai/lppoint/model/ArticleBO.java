package ren.lingpai.lppoint.model;

import ren.lingpai.lppoint.entity.ArticleDO;

import java.sql.Date;

/**
 * Created by lrp on 17-4-30.
 */
public class ArticleBO extends ArticleDO{

    /*//主键
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
    private int sort;*/

    private String types;
    private String coverImgStr;
    private String articleContentStr;

    /*public int getId() {
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

    */

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getCoverImgStr() {
        return coverImgStr;
    }

    public void setCoverImgStr(String coverImgStr) {
        this.coverImgStr = coverImgStr;
    }

    public String getArticleContentStr() {
        return articleContentStr;
    }

    public void setArticleContentStr(String articleContentStr) {
        this.articleContentStr = articleContentStr;
    }

    @Override
    public String toString() {
        return super.toString() + "ArticleBO{" +
                "articleContentStr='" + articleContentStr + '\'' +
                '}';
    }
}
