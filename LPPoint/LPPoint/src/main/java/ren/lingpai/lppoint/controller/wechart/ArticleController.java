package ren.lingpai.lppoint.controller.wechart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.lingpai.lpagile.annotation.*;
import ren.lingpai.lpagile.entity.LPData;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lpagile.plugin.security.annotation.User;
import ren.lingpai.lppoint.entity.ArticleDO;
import ren.lingpai.lppoint.model.ArticleBO;
import ren.lingpai.lppoint.service.ArticleService;
import ren.lingpai.lputil.clazz.ClassUtil;
import ren.lingpai.lputil.collection.CollectionUtil;

import java.sql.Date;
import java.util.List;

/**
 * Created by lrp on 17-3-5.
 */
@Controller("/wechart_article")
public class ArticleController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Inject
    private ArticleService articleService;

    /**
     * 进入微信文章页面
     * @return
     */
    @Get
    @Action("/article_waterfall_view")
    public LPView articleWaterFallView(){
        List<ArticleBO> articleList = articleService.getArticleList();
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
        return new LPView("wechart/article/article_waterfall.jsp").addModel("articleList",articleList);
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
        return new LPView("wechart/article/article_detail.jsp").addModel("article",articleBO);
    }

}
