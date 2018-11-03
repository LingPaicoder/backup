package ren.lingpai.lppoint.controller.visitor;

import ren.lingpai.lpagile.annotation.Action;
import ren.lingpai.lpagile.annotation.Controller;
import ren.lingpai.lpagile.annotation.Get;
import ren.lingpai.lpagile.entity.LPView;

/**
 * Created by lrp on 17-5-14.
 */
@Controller("/visitor_about")
public class AboutController {

    /**
     * 进入关于页面
     * @return
     */
    @Get
    @Action("/about_view")
    public LPView about(){
        return new LPView("visitor/about.jsp");
    }

}
