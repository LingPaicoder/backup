package ren.lingpai.lppoint.controller.visitor;

import ren.lingpai.lpagile.annotation.*;
import ren.lingpai.lpagile.entity.LPData;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lppoint.entity.TypeDO;
import ren.lingpai.lppoint.service.ArticleService;
import ren.lingpai.lppoint.service.TypeService;
import ren.lingpai.lputil.collection.CollectionUtil;

import java.util.List;

/**
 * Created by lrp on 17-5-14.
 */
@Controller("/visitor_index")
public class IndexController {

    @Inject
    private TypeService typeService;
    @Inject
    private ArticleService articleService;

    /**
     * 进入首页
     * @return
     */
    @Get
    @Action("/index_view")
    public LPView index(){
        List<TypeDO> typeDOList = typeService.getTypeList();
        String typeStr = "";
        if(CollectionUtil.isNotEmpty(typeDOList)){
            for (TypeDO typeDO : typeDOList){
                //{"id":"easy", "parentid":"root", "topic":"Easy", "direction":"left"},
                typeStr += "{\"id\":\"" +
                        typeDO.getId() +
                        "\", \"parentid\":\"" +
                        typeDO.getPId() +
                        "\", \"topic\":\"" +
                        typeDO.getTopic() +
                        "\", \"direction\":\"" +
                        typeDO.getDirection() +
                        "\"},";
            }
        }
        return new LPView("visitor/index.jsp").addModel("typeStr",typeStr);
    }

    /**
     * 根据Type查询文章个数
     * @param typeid
     * @return
     */
    @Post
    @Action("/get_article_num")
    public LPData getArticleNum(Integer typeid){
        long result = 0;
        if (typeid <= 0) return new LPData(result);
        result = articleService.getArticleNumByType(typeid);
        return new LPData(result);
    }

}
