package ren.lingpai.lppoint.service;

import ren.lingpai.lpagile.annotation.Service;
import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lppoint.entity.Article_TypeDO;

import java.util.List;
import java.util.Map;

/**
 * Created by lrp on 17-5-14.
 */
@Service
public class ArticleTypeService {

    /**
     * 根据Type获取文章列表
     * @return
     */
    public List<Article_TypeDO> getArticleTypeListByArticle(int articleId){
        String sql = "SELECT " +
                " m_id as id , m_articleid as articleId , m_typeid as typeId " +
                " FROM t_article_type " +
                " where m_articleid= ? ";
        return DataBasePart.queryEntityList(Article_TypeDO.class , sql , articleId);
    }

    /**
     * 删除ArticleType
     * @param id
     * @return
     */
    public boolean deleteArticleType(int id){
        return DataBasePart.deleteEntity(Article_TypeDO.class,id);
    }

    /**
     * 创建ArticleType
     * @param fieldMap
     * @return
     */
    public boolean createArticle(Map<String,Object> fieldMap){
        return DataBasePart.insertEntity(Article_TypeDO.class,fieldMap);
    }

}
