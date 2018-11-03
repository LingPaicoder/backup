package ren.lingpai.lppoint.service;

import ren.lingpai.lpagile.annotation.Inject;
import ren.lingpai.lpagile.annotation.Service;
import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lppoint.entity.ArticleDO;
import ren.lingpai.lppoint.entity.Article_TypeDO;
import ren.lingpai.lppoint.entity.MessageDO;
import ren.lingpai.lppoint.model.ArticleBO;
import ren.lingpai.lppoint.model.MessageBO;
import ren.lingpai.lppoint.util.PageParam;
import ren.lingpai.lputil.cast.CastUtil;
import ren.lingpai.lputil.clazz.ClassUtil;
import ren.lingpai.lputil.collection.CollectionUtil;

import java.util.List;
import java.util.Map;


/**
 * Created by lrp on 17-3-5.
 */
@Service
public class ArticleService {

    @Inject
    private ArticleTypeService articleTypeService;

    /**
     * 获取文章列表
     * @return
     */
    public List<ArticleBO> getArticleList(){
        String sql = "SELECT " +
                " m_id as id , m_title as title , m_creator as creator " +
                " , m_createtime as createTime , m_summary as summary , m_coverimg as coverImg " +
                " , m_articlecontent as articleContent , m_readnum as readNum , m_upnum as upNum , m_sort as sort " +
                " FROM t_article order by id DESC";
        return DataBasePart.queryEntityList(ArticleBO.class, sql);
    }

    /**
     * 获取文章列表前9条记录
     * @return
     */
    public List<ArticleBO> getArticleListLimitNine(){
        String sql = "SELECT " +
                " m_id as id , m_title as title , m_creator as creator " +
                " , m_createtime as createTime , m_summary as summary , m_coverimg as coverImg " +
                " , m_articlecontent as articleContent , m_readnum as readNum , m_upnum as upNum , m_sort as sort " +
                " FROM t_article order by id DESC " +
                " limit 0,9 ";
        return DataBasePart.queryEntityList(ArticleBO.class, sql);
    }

    /**
     * 分页获取文章列表
     * @return
     */
    public List<ArticleBO> getArticleListByPage(PageParam pageParam){

        String sqlCount = "SELECT count(*) " +
                " FROM t_article order by m_id DESC ";
        Long totalCount = DataBasePart.query(sqlCount);
        PageParam.setPageValues(CastUtil.castInt(totalCount),pageParam);

        String sql = "SELECT " +
                " m_id as id , m_title as title , m_creator as creator " +
                " , m_createtime as createTime , m_summary as summary , m_coverimg as coverImg " +
                " , m_articlecontent as articleContent , m_readnum as readNum , m_upnum as upNum , m_sort as sort " +
                " FROM t_article  order by id DESC " +
                " limit " + (pageParam.getCurrentPage()-1)*pageParam.getPageSize() + " , " + pageParam.getPageSize();
        return DataBasePart.queryEntityList(ArticleBO.class, sql);
    }

    /**
     * 根据Type获取文章列表
     * @return
     */
    public List<ArticleBO> getArticleListByType(int typeId){
        String sql = "SELECT " +
                " t_article.m_id as id , m_title as title , m_creator as creator " +
                " , m_createtime as createTime , m_summary as summary , m_coverimg as coverImg " +
                " , m_articlecontent as articleContent , m_readnum as readNum , m_upnum as upNum , m_sort as sort " +
                " FROM t_article " +
                " inner join t_article_type on t_article.m_id = t_article_type.m_articleid " +
                " where m_typeid= ? order by id DESC";
        return DataBasePart.queryEntityList(ArticleBO.class , sql , typeId);
    }

    /**
     * 根据Type获取文章个数
     * @return
     */
    public long getArticleNumByType(int typeId){
        String sql = " SELECT count(*) " +
                " FROM t_article " +
                " inner join t_article_type on t_article.m_id = t_article_type.m_articleid " +
                " where m_typeid= ? ";
        return DataBasePart.query(sql , typeId);
    }


    /**
     * 获取文章
     * @param id
     * @return
     */
    public ArticleBO getArticle(int id){
        String sql = "SELECT " +
                " m_id as id , m_title as title , m_creator as creator " +
                " , m_createtime as createTime , m_summary as summary , m_coverimg as coverImg " +
                " , m_articlecontent as articleContent , m_readnum as readNum , m_upnum as upNum , m_sort as sort " +
                " FROM t_article WHERE m_id = ?";
        return DataBasePart.queryEntity(ArticleBO.class,sql,id);
    }

    /**
     * 创建文章
     * @param fieldMap
     * @return
     */
    public boolean createArticle(Map<String,Object> fieldMap) throws Exception{
        ArticleDO articleDO = new ArticleDO();
        ClassUtil.copyField(fieldMap,articleDO);
        boolean result = DataBasePart.insertEntity(ArticleDO.class,ClassUtil.convertBeanToMap(articleDO));
        //向关联表t_article_type中插入数据
        try {
            if(null != fieldMap.get("types") && CastUtil.castString(fieldMap.get("types")).length() > 0){
                ArticleDO article = getLastArticleByTitle(CastUtil.castString(fieldMap.get("title")));
                if (null != article){
                    if(CastUtil.castString(fieldMap.get("types")).indexOf(",") > 0){
                        String[] types = CastUtil.castString(fieldMap.get("types")).split(",");
                        for (String s : types){
                            Article_TypeDO articleTypeDO = new Article_TypeDO();
                            articleTypeDO.setArticleId(article.getId());
                            articleTypeDO.setTypeId(CastUtil.castInt(s.trim()));
                            articleTypeService.createArticle(ClassUtil.convertBeanToMap(articleTypeDO));
                        }
                    }else {
                        Article_TypeDO articleTypeDO = new Article_TypeDO();
                        articleTypeDO.setArticleId(article.getId());
                        articleTypeDO.setTypeId(CastUtil.castInt(CastUtil.castString(fieldMap.get("types")).trim()));
                        articleTypeService.createArticle(ClassUtil.convertBeanToMap(articleTypeDO));
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新文章
     * @param id
     * @param fieldMap
     * @return
     */
    public boolean updateArticle(int id,Map<String,Object> fieldMap) throws Exception{
        //删除关联表t_article_type的数据
        List<Article_TypeDO> articleTypeDOList = articleTypeService.getArticleTypeListByArticle(id);
        if (CollectionUtil.isNotEmpty(articleTypeDOList)){
            for (Article_TypeDO articleTypeDO : articleTypeDOList){
                articleTypeService.deleteArticleType(articleTypeDO.getId());
            }
        }
        //向关联表t_article_type中插入数据
        try {
            if(null != fieldMap.get("types") && CastUtil.castString(fieldMap.get("types")).length() > 0){
                if (CastUtil.castString(fieldMap.get("types")).indexOf(",") > 0){
                    String[] types = CastUtil.castString(fieldMap.get("types")).split(",");
                    for (String s : types){
                        Article_TypeDO articleTypeDO = new Article_TypeDO();
                        articleTypeDO.setArticleId(id);
                        articleTypeDO.setTypeId(CastUtil.castInt(s.trim()));
                        articleTypeService.createArticle(ClassUtil.convertBeanToMap(articleTypeDO));
                    }
                }else{
                    Article_TypeDO articleTypeDO = new Article_TypeDO();
                    articleTypeDO.setArticleId(id);
                    articleTypeDO.setTypeId(CastUtil.castInt(CastUtil.castString(fieldMap.get("types")).trim()));
                    articleTypeService.createArticle(ClassUtil.convertBeanToMap(articleTypeDO));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ArticleDO articleDO = new ArticleDO();
        ClassUtil.copyField(fieldMap,articleDO);
        return DataBasePart.updateEntity(ArticleDO.class,id,ClassUtil.convertBeanToMap(articleDO));
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    public boolean deleteArticle(int id){
        //删除关联表t_article_type的数据
        List<Article_TypeDO> articleTypeDOList = articleTypeService.getArticleTypeListByArticle(id);
        if (CollectionUtil.isNotEmpty(articleTypeDOList)){
            for (Article_TypeDO articleTypeDO : articleTypeDOList){
                articleTypeService.deleteArticleType(articleTypeDO.getId());
            }
        }

        //删除相应留言
        //获取第一级留言
        String sql = "SELECT " +
                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                " m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                " FROM t_message " +
                " where t_message.m_articleid = ? order by id DESC ";
        List<MessageBO> messageBOList = DataBasePart.queryEntityList(MessageBO.class, sql ,id);

        //获取第二级留言
        if (CollectionUtil.isNotEmpty(messageBOList)){
            for (MessageBO messageBO : messageBOList){
                String sql2 = "SELECT " +
                        " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                        " m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                        " FROM t_message " +
                        " where m_pmsgid = ? order by id DESC ";
                List<MessageBO> messageBO2List = DataBasePart.queryEntityList(MessageBO.class, sql2 , messageBO.getId());
                messageBO.setSubMessages(messageBO2List);

                //获取第三级留言
                if(CollectionUtil.isNotEmpty(messageBO2List)){
                    for (MessageBO messageBO2 : messageBO2List){
                        String sql3 = "SELECT " +
                                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                " m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                                " FROM t_message " +
                                " where m_pmsgid = ? order by id DESC ";
                        List<MessageBO> messageBO3List = DataBasePart.queryEntityList(MessageBO.class, sql3 , messageBO2.getId());
                        messageBO2.setSubMessages(messageBO3List);

                        //获取第四级留言
                        if(CollectionUtil.isNotEmpty(messageBO3List)){
                            for (MessageBO messageBO3 : messageBO3List){
                                String sql4 = "SELECT " +
                                        " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                        " m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                                        " FROM t_message " +
                                        " where m_pmsgid = ? order by id DESC ";
                                List<MessageBO> messageBO4List = DataBasePart.queryEntityList(MessageBO.class, sql4 , messageBO3.getId());
                                messageBO3.setSubMessages(messageBO4List);

                                //获取第五级留言
                                if(CollectionUtil.isNotEmpty(messageBO4List)){
                                    for (MessageBO messageBO4 : messageBO4List) {
                                        String sql5 = "SELECT " +
                                                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                                " m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                                                " FROM t_message " +
                                                " where m_pmsgid = ? order by id DESC ";
                                        List<MessageBO> messageBO5List = DataBasePart.queryEntityList(MessageBO.class, sql5, messageBO4.getId());
                                        messageBO4.setSubMessages(messageBO5List);

                                        if(CollectionUtil.isNotEmpty(messageBO5List)){
                                            for (MessageBO messageBO5 : messageBO5List){
                                                DataBasePart.deleteEntity(MessageDO.class,messageBO5.getId());
                                            }
                                        }

                                    }
                                }

                                if(CollectionUtil.isNotEmpty(messageBO4List)){
                                    for (MessageBO messageBO4 : messageBO4List){
                                        DataBasePart.deleteEntity(MessageDO.class,messageBO4.getId());
                                    }
                                }

                            }
                        }

                        if(CollectionUtil.isNotEmpty(messageBO3List)){
                            for (MessageBO messageBO3 : messageBO3List){
                                DataBasePart.deleteEntity(MessageDO.class,messageBO3.getId());
                            }
                        }

                    }
                }

                if(CollectionUtil.isNotEmpty(messageBO2List)){
                    for (MessageBO messageBO2 : messageBO2List){
                        DataBasePart.deleteEntity(MessageDO.class,messageBO2.getId());
                    }
                }

            }
        }
        if(CollectionUtil.isNotEmpty(messageBOList)){
            for (MessageBO messageBO : messageBOList){
                DataBasePart.deleteEntity(MessageDO.class,messageBO.getId());
            }
        }

        return DataBasePart.deleteEntity(ArticleDO.class,id);
    }

    /**
     * 根据Title查询最新一次插入的文章
     * @param title
     * @return
     */
    public ArticleDO getLastArticleByTitle(String title){
        ArticleDO result = null;
        String sql = "SELECT " +
                " m_id as id , m_title as title , m_creator as creator " +
                " , m_createtime as createTime , m_summary as summary , m_coverimg as coverImg " +
                " , m_articlecontent as articleContent , m_readnum as readNum , m_upnum as upNum , m_sort as sort " +
                " FROM t_article WHERE m_title = ? order by id DESC ";
        List<ArticleDO> articleDOList =  DataBasePart.queryEntityList(ArticleDO.class,sql,title);
        if (CollectionUtil.isNotEmpty(articleDOList)){
            result = articleDOList.get(0);
        }
        return result;
    }
    
}
