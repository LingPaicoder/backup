package ren.lingpai.lppoint.service;

import java.util.List;
import java.util.Map;

import ren.lingpai.lpagile.annotation.Inject;
import ren.lingpai.lpagile.annotation.Service;
import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lppoint.entity.ArticleDO;
import ren.lingpai.lppoint.entity.Article_TypeDO;
import ren.lingpai.lppoint.entity.MessageDO;
import ren.lingpai.lppoint.entity.MindArticleDO;
import ren.lingpai.lppoint.model.ArticleBO;
import ren.lingpai.lppoint.model.MessageBO;
import ren.lingpai.lppoint.util.PageParam;
import ren.lingpai.lputil.cast.CastUtil;
import ren.lingpai.lputil.clazz.ClassUtil;
import ren.lingpai.lputil.collection.CollectionUtil;


/**
 * Created by lrp on 17-3-5.
 */
@Service
public class MindArticleService {

    /**
     * 根据Type获取文章列表
     */
    public List<MindArticleDO> getStarArticleList(){
        String sql = "SELECT " +
                " m_id as id , m_title as title , m_summary as summary " +
                " , m_cover_img_url as coverImgUrl , m_url as url , m_type_id as typeId " +
                " , m_sort as sort " +
                " FROM t_mind_article " +
                " where m_star= ? order by m_sort DESC";
        return DataBasePart.queryEntityList(MindArticleDO.class , sql , 1);
    }

    /**
     * 根据Type获取文章列表
     */
    public List<MindArticleDO> getArticleListByType(int typeId){
        String sql = "SELECT " +
                " m_id as id , m_title as title , m_summary as summary " +
                " , m_cover_img_url as coverImgUrl , m_url as url , m_type_id as typeId " +
                " , m_sort as sort " +
                " FROM t_mind_article " +
                " where m_type_id= ? order by m_sort DESC";
        return DataBasePart.queryEntityList(MindArticleDO.class , sql , typeId);
    }

    /**
     * 根据Type获取文章个数
     */
    public long getArticleNumByType(int typeId){
        String sql = " SELECT count(*) " +
                " FROM t_mind_article " +
                " where m_type_id= ? ";
        return DataBasePart.query(sql , typeId);
    }

}
