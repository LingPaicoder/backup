package ren.lingpai.lppoint.controller.mind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ren.lingpai.lpagile.annotation.Action;
import ren.lingpai.lpagile.annotation.Controller;
import ren.lingpai.lpagile.annotation.Get;
import ren.lingpai.lpagile.annotation.Inject;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lppoint.entity.MindArticleDO;
import ren.lingpai.lppoint.model.ArticleBO;
import ren.lingpai.lppoint.model.MessageBO;
import ren.lingpai.lppoint.service.ArticleService;
import ren.lingpai.lppoint.service.MessageService;
import ren.lingpai.lppoint.service.MindArticleService;
import ren.lingpai.lppoint.util.PageParam;
import ren.lingpai.lppoint.util.RandomUtil;
import ren.lingpai.lputil.collection.CollectionUtil;

/**
 * Created by lrp on 17-3-5.
 */
@Controller("/article")
public class ArticleController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Inject
    private MindArticleService articleService;

    /**
     * 进入前台文章页面
     */
    @Get
    @Action("/list")
    public LPView list(Integer typeId){
        List<MindArticleDO> articleList =articleService.getArticleListByType(typeId);
        return new LPView("mind/list.jsp").addModel("articleList",articleList);
    }

    /**
     * 随机好文
     */
    @Get
    @Action("/random")
    public LPView random(){
        List<MindArticleDO> articleList =articleService.getStarArticleList();
        int size = articleList.size();
        int index = RandomUtil.nextInt(0,size);
        return new LPView("mind/list.jsp").addModel("articleList",
                Collections.singletonList(articleList.get(index)));
    }

}
