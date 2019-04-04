package ren.lingpai.lppoint.controller.mind;

import java.util.List;

import ren.lingpai.lpagile.annotation.Action;
import ren.lingpai.lpagile.annotation.Controller;
import ren.lingpai.lpagile.annotation.Get;
import ren.lingpai.lpagile.annotation.Inject;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lppoint.entity.TypeDO;
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

    /**
     * 进入首页
     */
    @Get
    @Action("/index")
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
        return new LPView("mind/index.jsp").addModel("typeStr",typeStr);
    }

}
