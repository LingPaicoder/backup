package ren.lingpai.lpagiledemo.controller;

import ren.lingpai.lpagile.annotation.Action;
import ren.lingpai.lpagile.annotation.Controller;
import ren.lingpai.lpagile.annotation.Get;
import ren.lingpai.lpagile.annotation.Inject;
import ren.lingpai.lpagile.entity.RequestParam;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lpagiledemo.model.Article;
import ren.lingpai.lpagiledemo.service.ArticleService;

import java.util.List;

/**
 * Created by lrp on 17-3-5.
 */
@Controller("")
public class ArticleController {

    @Inject
    private ArticleService articleService;

    /**
     * 进入文章列表页面
     * @param param
     * @return
     */
    @Get
    @Action("/article_list")
    public LPView articleList(RequestParam param){
        List<Article> articleList = articleService.getArticleList();
        return new LPView("article.jsp").addModel("articleList",articleList);
    }

}
