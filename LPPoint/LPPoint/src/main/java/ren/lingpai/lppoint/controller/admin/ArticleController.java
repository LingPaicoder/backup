package ren.lingpai.lppoint.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.annotation.*;
import ren.lingpai.lpagile.entity.LPData;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lpagile.part.ContextPart;
import ren.lingpai.lpagile.plugin.security.annotation.User;
import ren.lingpai.lppoint.entity.ArticleDO;
import ren.lingpai.lppoint.entity.Article_TypeDO;
import ren.lingpai.lppoint.entity.UserDO;
import ren.lingpai.lppoint.service.ArticleService;
import ren.lingpai.lppoint.model.ArticleBO;
import ren.lingpai.lppoint.service.ArticleTypeService;
import ren.lingpai.lppoint.util.PageParam;
import ren.lingpai.lputil.clazz.ClassUtil;
import ren.lingpai.lputil.collection.CollectionUtil;

import java.sql.Date;
import java.util.List;

/**
 * Created by lrp on 17-3-5.
 */
@Controller("/admin_article")
public class ArticleController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Inject
    private ArticleService articleService;
    @Inject
    private ArticleTypeService articleTypeService;

    /**
     * 进入后台文章列表页面
     * @return
     */
    @User
    @Get
    @Action("/article_list_view")
    public LPView articleList(Integer currentPage){
        currentPage = (currentPage!=null)?currentPage:1;
        PageParam pageParam = PageParam.NewInstance(currentPage);

        if(!"LingPaicoder".equals(((UserDO)ContextPart.getSessionAttribute("currentUser")).getUsername())){
            return new LPView("404.jsp");
        }

        List<ArticleBO> articleList = articleService.getArticleListByPage(pageParam);
        if (CollectionUtil.isNotEmpty(articleList)){
            try {
                for (ArticleBO articleBO : articleList){
                    if((null != articleBO.getCoverImg())
                            && (articleBO.getCoverImg().length > 0)){
                        articleBO.setCoverImgStr(new String(articleBO.getCoverImg(),"UTF-8"));
                    }
                    if((null != articleBO.getArticleContent())
                            && (articleBO.getArticleContent().length > 0)){
                        articleBO.setArticleContentStr(new String(articleBO.getArticleContent(),"UTF-8"));
                    }
                }
            }catch (Exception e){
                LOGGER.error("------[article_list_view]exception------",e);
            }
        }
        return new LPView("admin/article/article_list.jsp")
                .addModel("articleList",articleList)
                .addModel("pager",pageParam);
    }

    /**
     * 进入新建文章页面
     * @return
     */
    @User
    @Get
    @Action("/article_add_view")
    public LPView addArticleView(){
        return new LPView("admin/article/article_add.jsp");
    }

    /**
     * 进入编辑文章页面
     * @param id
     * @return
     */
    @User
    @Get
    @Action("/article_edit_view")
    public LPView editArticleView(Integer id){
        ArticleBO articleBO = articleService.getArticle(id);
        if (null != articleBO){
            try {
                if((null != articleBO.getCoverImg())
                        && (articleBO.getCoverImg().length > 0)){
                    articleBO.setCoverImgStr(new String(articleBO.getCoverImg(),"UTF-8"));
                }
                if((null != articleBO.getArticleContent())
                        && (articleBO.getArticleContent().length > 0)){
                    articleBO.setArticleContentStr(new String(articleBO.getArticleContent(),"UTF-8"));
                }
            }catch (Exception e){
                LOGGER.error("------[article_detail_view]exception------",e);
            }
        }
        String types = "";
        List<Article_TypeDO> article_typeDOList = articleTypeService.getArticleTypeListByArticle(id);
        if (CollectionUtil.isNotEmpty(article_typeDOList)){
            for (Article_TypeDO article_typeDO : article_typeDOList){
                types += article_typeDO.getTypeId() + ",";
            }
            types = types.substring(0,types.length()-1);
        }
        articleBO.setTypes(types);
        return new LPView("admin/article/article_edit.jsp").addModel("article",articleBO);
    }

    /**
     * 文章详情
     * @param id
     * @return
     */
    @Get
    @Action("/article_detail_view")
    public LPView articleDetailView(Integer id){
        ArticleBO articleBO = articleService.getArticle(id);
        if (null != articleBO){
            try {
                    if((null != articleBO.getCoverImg())
                            && (articleBO.getCoverImg().length > 0)){
                        articleBO.setCoverImgStr(new String(articleBO.getCoverImg(),"UTF-8"));
                    }
                    if((null != articleBO.getArticleContent())
                            && (articleBO.getArticleContent().length > 0)){
                        articleBO.setArticleContentStr(new String(articleBO.getArticleContent(),"UTF-8"));
                    }
            }catch (Exception e){
                LOGGER.error("------[article_detail_view]exception------",e);
            }
        }
        return new LPView("admin/article/article_detail.jsp").addModel("article",articleBO);
    }

    /**
     * 添加文章
     * @param articleBO
     * @return
     */
    @User
    @Post
    @Action("/article_add")
    public LPData addArticle(ArticleBO articleBO){
        boolean result = false;
        if (null == articleBO) return new LPData(result);
        articleBO.setCreateTime(new Date(new java.util.Date().getTime()));
        try {
            result = articleService.createArticle(ClassUtil.convertBeanToMap(articleBO));
        }catch (Exception e){
            LOGGER.error("------</article_add>exception------",e);
        }
        return new LPData(result);
    }

    /**
     * 更新文章
     * @param articleBO
     * @return
     */
    @User
    @Post
    @Action("/article_edit")
    public LPData editArticle(ArticleBO articleBO){
        boolean result = false;
        if (null == articleBO) return new LPData(result);
        articleBO.setCreateTime(new Date(new java.util.Date().getTime()));
        try {
            result = articleService.updateArticle(articleBO.getId(),ClassUtil.convertBeanToMap(articleBO));
        }catch (Exception e){
            LOGGER.error("------</article_edit>exception------",e);
        }
        return new LPData(result);
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @User
    @Post
    @Action("/article_delete")
    public LPData deleteArticle(Integer id){
        boolean result = false;
        if (id <= 0) return new LPData(result);
        try {
            result = articleService.deleteArticle(id);
        }catch (Exception e){
            LOGGER.error("------</article_delete>exception------",e);
        }
        return new LPData(result);
    }

}
