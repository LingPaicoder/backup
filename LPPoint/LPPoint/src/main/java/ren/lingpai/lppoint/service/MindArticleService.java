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
     * @return
     */
    public List<MindArticleDO> getArticleListByType(int typeId){


       /* `m_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
                `m_title` varchar(1024) NOT NULL DEFAULT '' COMMENT '标题',
                `m_summary` varchar(1024) NOT NULL DEFAULT '' COMMENT '摘要',
                `m_cover_img_url` varchar(1024) NOT NULL DEFAULT '' COMMENT '封面图片地址',
                `m_url` varchar(1024) NOT NULL DEFAULT '' COMMENT '内容地址',
                `m_type_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '类别id',
                `m_sort` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序',*/

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
     * @return
     */
    public long getArticleNumByType(int typeId){
        String sql = " SELECT count(*) " +
                " FROM t_mind_article " +
                " where m_type_id= ? ";
        return DataBasePart.query(sql , typeId);
    }

}
