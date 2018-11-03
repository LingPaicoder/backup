package ren.lingpai.lpagiledemo.service;

import ren.lingpai.lpagile.annotation.Service;
import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lpagiledemo.entity.ProvenceDO;

import java.util.List;
import java.util.Map;


/**
 * Created on 17-3-5.
 */
@Service
public class ProvenceService {

    /**
     * 获取文章列表
     * @return
     */
    public List<ProvenceDO> getArticleList(){
        String sql = "SELECT " +
                " m_id as id , m_title as title , m_typeid as typeId , m_creator as creator " +
                " , m_createtime as createTime , m_summary as summary , m_coverimg as coverImg " +
                " , m_articlecontent as articleContent , m_readnum as readNum , m_upnum as upNum , m_sort as sort " +
                " FROM t_article order by id DESC";
        return DataBasePart.queryEntityList(ProvenceDO.class, sql);
    }

    /**
     * 获取文章
     * @param id
     * @return
     */
    public ProvenceDO getArticle(int id){
        String sql = "SELECT " +
                " m_id as id , m_title as title , m_typeid as typeId , m_creator as creator " +
                " , m_createtime as createTime , m_summary as summary , m_coverimg as coverImg " +
                " , m_articlecontent as articleContent , m_readnum as readNum , m_upnum as upNum , m_sort as sort " +
                " FROM t_article WHERE m_id = ?";
        return DataBasePart.queryEntity(ProvenceDO.class,sql,id);
    }

    /**
     * 创建客户
     * @param fieldMap
     * @return
     */
    public boolean createArticle(Map<String,Object> fieldMap){
        return DataBasePart.insertEntity(ProvenceDO.class,fieldMap);
    }

    /**
     * 更新客户
     * @param id
     * @param fieldMap
     * @return
     */
    public boolean updateArticle(int id,Map<String,Object> fieldMap){
        return DataBasePart.updateEntity(ProvenceDO.class,id,fieldMap);
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    public boolean deleteArticle(int id){
        return DataBasePart.deleteEntity(ProvenceDO.class,id);
    }
    
}
