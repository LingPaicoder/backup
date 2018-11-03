package ren.lingpai.lpagiledemo.controller;

import ren.lingpai.lpagile.annotation.Action;
import ren.lingpai.lpagile.annotation.Controller;
import ren.lingpai.lpagile.annotation.Get;
import ren.lingpai.lpagile.annotation.Inject;
import ren.lingpai.lpagile.entity.LPData;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lpagile.entity.RequestParam;
import ren.lingpai.lpagiledemo.Data;
import ren.lingpai.lpagiledemo.entity.DetailDO;
import ren.lingpai.lpagiledemo.model.Article;
import ren.lingpai.lpagiledemo.model.DetailBO;
import ren.lingpai.lpagiledemo.service.DetailService;
import ren.lingpai.lputil.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 17-3-5.
 */
@Controller("")
public class DetailController {

    @Inject
    private DetailService detailService;

    /**
     * 进入专利列表页面
     * @param param
     * @return
     */
    @Get
    @Action("/detail_list")
    public LPView detailList(RequestParam param){
        List<DetailDO> detailList = detailService.getDetailList();
        return new LPView("article.jsp").addModel("articleList",detailList);
    }

    @Get
    @Action("/test")
    public LPData test(){
        LPData data = null;
        try {
            //1.返回专利申请最小年份和最大年份
            //Map<String,String > result = detailService.getDetailDateMaxAndMin();

            //2.返回每年申请量
            //Map<String,List<Long>> result =  detailService.getNumByYear();

            //3.返回每省的申请量
            //Map<String ,List<Map<String ,String>>> result = detailService.getNumByProvence();

            //4.返回每个类别的申请量
            //List<Map<String,List<String>>> result = detailService.getNumByType();

            //5.返回每个省每个类别的申请量
            //Map<String,Object> result = detailService.getNumByProvenceAndType();

            //6.返回每年每省的申请量
            //Map<String,Object> result = detailService.getNumByProvenceAndYear();

            //7.返回每年每类别的申请量
            //Map<String,Object> result = detailService.getNumByTypeAndYear();

            //8.返回每个类别的单位
            //Map<String,Object> result = detailService.getUtilByType();

            //9.返回每个单位申请的专利详情
            /*String unitName = "单位1";
            List<DetailBO> result = new ArrayList<DetailBO>();
            List<DetailDO> details = detailService.getDetailByUnit(unitName);
            if(CollectionUtil.isNotEmpty(details)){
                for(DetailDO detailDO : details){
                    DetailBO detailBO = detailService.parseDetailDOToBO(detailDO);
                    result.add(detailBO);
                }
            }*/

            //10.返回每个类别的推荐类别
            /*String unitName = "单位1";
            String typeName = "种类1";
            String result = detailService.getRecommendType(unitName,typeName);
            */

            //11.每省每年的申请质量
            //Map<String,Object> result = detailService.getQualityNumByProvenceAndYear();

            //12.每类别每年的申请质量
            //Map<String,Object> result = detailService.getQualityNumByTypeAndYear();

            //13.每年的申请质量
            //Map<String,Object> result = detailService.getQualityNumByYear();

            //14.每类别的申请质量
            //Map<String,Object> result = detailService.getQualityNumByType();

            //15.每省的申请质量
            Map<String,Object> result = detailService.getQualityNumByProvence();

            //System.out.println(result);

            //导数据
            Data.mainMethod(detailService);
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
