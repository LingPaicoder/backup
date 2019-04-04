package ren.lingpai.lppoint.controller.mind;

import java.util.List;

import ren.lingpai.lpagile.annotation.Action;
import ren.lingpai.lpagile.annotation.Controller;
import ren.lingpai.lpagile.annotation.Get;
import ren.lingpai.lpagile.annotation.Inject;
import ren.lingpai.lpagile.annotation.Post;
import ren.lingpai.lpagile.entity.LPData;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lppoint.entity.TypeDO;
import ren.lingpai.lppoint.service.MindArticleService;
import ren.lingpai.lppoint.service.TypeService;
import ren.lingpai.lputil.collection.CollectionUtil;

/**
 * @author liurenpeng
 * @date Created in 19-4-4
 */
@Controller("/mind")
public class MindController {

    @Inject
    private TypeService typeService;
    @Inject
    private MindArticleService articleService;

    /**
     * 进入首页
     */
    @Get
    @Action("/index")
    public LPView index(Integer pId) {
        if (pId == null || pId <= 0) {
            pId = 1;
        }
        System.out.println("pId:" + pId);
        TypeDO pType = typeService.getById(pId);
        List<TypeDO> sonTypeList = typeService.getListByPid(pId);
        String typeStr = "{\"id\":\"" + pType.getId() + "\",\"isroot\":true, \"topic\":\""
                + pType.getTopic() + "\"},";
        if (CollectionUtil.isNotEmpty(sonTypeList)) {
            for (TypeDO typeDO : sonTypeList) {
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
        System.out.println("typeStr:" + typeStr);
        return new LPView("mind/index.jsp").addModel("typeStr", typeStr);
    }

    /**
     * 进入首页
     */
    @Get
    @Action("/sonNum")
    public LPData sonNum(Integer pId) {
        if (pId == null || pId <= 0) {
            pId = 1;
        }
        System.out.println("pId:" + pId);
        return new LPData(typeService.getListByPid(pId).size());
    }

    /**
     * 进入首页
     */
    @Get
    @Action("/articleNum")
    public LPData articleNum(Integer pId) {
        if (pId == null || pId <= 0) {
            pId = 1;
        }
        System.out.println("pId:" + pId);
        return new LPData(articleService.getArticleNumByType(pId));
    }

}
