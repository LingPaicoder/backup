package ren.lingpai.lpagiledemo.service;

import ren.lingpai.lpagile.annotation.Service;
import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lpagiledemo.model.Article;

import java.util.List;
import java.util.Map;


/**
 * Created by lrp on 17-3-5.
 */
@Service
public class ArticleService {

    /**
     * 获取文章列表
     * @return
     */
    public List<Article> getArticleList(){
        String sql = "SELECT * FROM article";
        return DataBasePart.queryEntityList(Article.class, sql);
    }

    /**
     * 获取文章
     * @param id
     * @return
     */
    public Article getArticle(int id){
        String sql = "SELECT * FROM article WHERE id = ?";
        return DataBasePart.queryEntity(Article.class,sql,id);
    }

    /**
     * 创建客户
     * @param fieldMap
     * @return
     */
    public boolean createArticle(Map<String,Object> fieldMap){
        return DataBasePart.insertEntity(Article.class,fieldMap);
    }

    /**
     * 更新客户
     * @param id
     * @param fieldMap
     * @return
     */
    public boolean updateCustomer(int id,Map<String,Object> fieldMap){
        return DataBasePart.updateEntity(Article.class,id,fieldMap);
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    public boolean deleteCustomer(int id){
        return DataBasePart.deleteEntity(Article.class,id);
    }
    
}
