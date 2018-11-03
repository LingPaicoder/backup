package ren.lingpai.lpagiledemo;

import ren.lingpai.lpagiledemo.entity.*;
import ren.lingpai.lpagiledemo.service.DetailService;
import ren.lingpai.lputil.collection.CollectionUtil;
import sun.net.www.ParseUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lrp on 17-6-15.
 */
public class Data {

    public static final String path = "/home/lrp/temp/data/";
    public static Map<String,Integer> provenceMap = new HashMap<String, Integer>();
    public static Map<String,CityDO> cityMap = new HashMap<String, CityDO>();
    public static Map<String,UnitDO> unitMap = new HashMap<String, UnitDO>();
    public static Map<String,TypeDO> typeMap = new HashMap<String, TypeDO>();

    public static DataBO putDataBO(DataBO dataBO,String[] lineArr){
        try {
            dataBO.setM0_序号(lineArr[0].substring(1,lineArr[0].length()));
            dataBO.setM1_申请号(lineArr[1].substring(1,lineArr[1].length()));
            dataBO.setM2_专利名称(lineArr[2].substring(1,lineArr[2].length()));
            dataBO.setM3_申请日(lineArr[3].substring(1,lineArr[3].length()));
            dataBO.setM4_法律状态(lineArr[4].substring(1,lineArr[4].length()));
            dataBO.setM5_主分类号(lineArr[5].substring(1,lineArr[5].length()));
            dataBO.setM6_分类号(lineArr[6].substring(1,lineArr[6].length()));
            dataBO.setM7_申请人(lineArr[7].substring(1,lineArr[7].length()));
            dataBO.setM8_发明人(lineArr[8].substring(1,lineArr[8].length()));
            dataBO.setM9_公开号(lineArr[9].substring(1,lineArr[9].length()));
            dataBO.setM10_公开日(lineArr[10].substring(1,lineArr[10].length()));
            dataBO.setM11_代理机构(lineArr[11].substring(1,lineArr[11].length()));
            dataBO.setM12_代理人(lineArr[12].substring(1,lineArr[12].length()));
            dataBO.setM13_地址(lineArr[13].substring(1,lineArr[13].length()));
            dataBO.setM14_优先权(lineArr[14].substring(1,lineArr[14].length()));
            dataBO.setM15_国省代码(lineArr[15].substring(1,lineArr[15].length()));
            dataBO.setM16_摘要(lineArr[16].substring(1,lineArr[16].length()));
            dataBO.setM17_主权项(lineArr[17].substring(1,lineArr[17].length()));
            dataBO.setM18_国际申请号(lineArr[18].substring(1,lineArr[18].length()));
            dataBO.setM19_国际申请日(lineArr[19].substring(1,lineArr[19].length()));
            dataBO.setM20_国际公布号(lineArr[20].substring(1,lineArr[20].length()));
            dataBO.setM21_国际公布日(lineArr[21].substring(1,lineArr[21].length()));
            dataBO.setM22_进入国家日期(lineArr[22].substring(1,lineArr[22].length()-1));
        }catch (Exception e){
            e.printStackTrace();
        }

        return dataBO;
    }

    public static void bootstrap(DetailService detailService){
        //初始化provenceMap
        List<ProvenceDO> provenceDOs = detailService.getAllProvence();
        if (CollectionUtil.isNotEmpty(provenceDOs)){
            for (ProvenceDO provenceDO : provenceDOs){
                provenceMap.put(provenceDO.getProvenceName(),provenceDO.getProvenceId());
            }
        }
        //初始化cityMap
        List<CityDO> cityDOs = detailService.getAllCity();
        if (CollectionUtil.isNotEmpty(cityDOs)){
            for (CityDO cityDO : cityDOs){
                cityMap.put(cityDO.getCityName(),cityDO);
            }
        }
        //初始化unitMap
        List<UnitDO> unitDOs = detailService.getUnitList();
        if (CollectionUtil.isNotEmpty(unitDOs)){
            for (UnitDO unitDO : unitDOs){
                unitMap.put(unitDO.getUnitName(),unitDO);
            }
        }
        //初始化typeMap
        List<TypeDO> typeDOs = detailService.getTypeList();
        if (CollectionUtil.isNotEmpty(typeDOs)){
            for (TypeDO typeDO : typeDOs){
                typeMap.put(typeDO.getTypeName(),typeDO);
            }
        }

        System.out.println("provenceMap.size():" + provenceMap.size());
        System.out.println("cityMap.size():" + cityMap.size());
        System.out.println("unitMap.size():" + unitMap.size());
        System.out.println("typeMap.size():" + typeMap.size());
        System.out.println("bootstrap over");

    }

    //对省进行处理
    public static int doProvence(DetailService detailService,String provenceName){
        Integer result = provenceMap.get(provenceName);
        if (null == result || result <= 0){
            System.out.println("provenceName:" + provenceName);
            if (detailService.createProvence(provenceName)){
                System.out.println("省信息插入成功");
                ProvenceDO provenceDO = detailService.getProvence(provenceName);
                if (null != provenceDO){
                    provenceMap.put(provenceName,provenceDO.getProvenceId());
                    return provenceDO.getProvenceId();
                }
            }
        }
        return result;
    }

    //对类别进行处理
    public static int doType(DetailService detailService,String typeName){
        TypeDO typeDO = typeMap.get(typeName);
        if (null == typeDO || typeDO.getTypeId() <= 0){
            System.out.println("typeName:" + typeName);
            if (detailService.createType(typeName)){
                System.out.println("类别信息插入成功");
                typeDO = detailService.getType(typeName);
                if (null != typeDO){
                    typeMap.put(typeName,typeDO);
                    return typeDO.getTypeId();
                }
            }
        }
        return typeDO.getTypeId();
    }

    //对单位进行处理
    public static int doUnit(DetailService detailService,String unitName){
        UnitDO unitDO = unitMap.get(unitName);
        if (null == unitDO || unitDO.getUnitId() <= 0){
            System.out.println("unitName:" + unitName);
            if (detailService.createUnit(unitName)){
                System.out.println("单位信息插入成功");
                unitDO = detailService.getUnit(unitName);
                if (null != unitDO){
                    unitMap.put(unitName,unitDO);
                    return unitDO.getUnitId();
                }
            }
        }
        return unitDO.getUnitId();
    }

    public static boolean checkProvence(String provenceName){
        String provence = "北京市，天津市，上海市，重庆市，河北省，山西省，辽宁省，" +
                "吉林省，黑龙江省，江苏省，浙江省，安徽省，福建省，江西省，山东省，" +
                "河南省，湖北省，湖南省，广东省，海南省，四川省，贵州省，云南省，" +
                "陕西省，甘肃省，青海省，台湾省，内蒙古自治区，广西壮族自治区，" +
                "西藏自治区，宁夏回族自治区，新疆维吾尔自治区，香港特别行政区，澳门特别行政区";
        return (provence.contains(provenceName));
    }

    public static void mainMethod(DetailService detailService) throws Exception {

        bootstrap(detailService);
        System.out.println("初始化完毕");

        //1,100,200,300,400,500,600,700,800,900,1000
        //1100,1200,1300,1400,1500,1600,1700,1800,1900
        for (int i = 100  ; /*i <= 1915 */ i <= 1900; i=i+100){
            String fileName = "patent_baiten_2014 ("+ i +").csv";
            String filePath = path + fileName;
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath)));

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);
                String[] lineArr = line.split("\";");
                System.out.println("lineArr.length" + lineArr.length);
                if (lineArr.length >= 23 && !("\"序号".equals(lineArr[0])) ){
                    DataBO dataBO = putDataBO(new DataBO(),lineArr);
                    //省
                    if (dataBO.getM15_国省代码().indexOf('(') <= 0){
                        continue;
                    }
                    String provenceData = dataBO.getM15_国省代码().substring(0,dataBO.getM15_国省代码().indexOf('('));
                    if (!checkProvence(provenceData)){
                        continue;
                    }
                    //类别
                    String typeData = dataBO.getM5_主分类号().substring(0,1);
                    //单位
                    String unitData = dataBO.getM7_申请人();
                    System.out.println("省、类别、单位信息获得如下：provenceData=" + provenceData
                        + ",typeData=" + typeData + ",unitData=" + unitData);

                    //对省进行处理
                    int provenceId = doProvence(detailService,provenceData);
                    //对类别进行处理
                    int typeId = doType(detailService,typeData);
                    //对单位进行处理
                    int unitId = doUnit(detailService,unitData);
                    System.out.println("对省、类别、单位处理完毕");

                    //对专利数据和关系表进行处理
                    Type_UnitDO type_unitDO = new Type_UnitDO();
                    type_unitDO.setTypeId(typeId);
                    type_unitDO.setUnitId(unitId);
                    System.out.println("type_unitDO:" + type_unitDO.toString());
                    boolean result0 = detailService.createType_Unit(type_unitDO);
                    System.out.println("插入结果：" + result0);

                    DetailDO detailDO = new DetailDO();
                    detailDO.setPatentId(dataBO.getM1_申请号());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    detailDO.setDate(simpleDateFormat.parse(dataBO.getM3_申请日()));
                    detailDO.setCityId(1);
                    detailDO.setProvenceId(provenceId);
                    detailDO.setUnitId(unitId);
                    detailDO.setTypeId(typeId);
                    detailDO.setStatus("有效专利".equals(dataBO.getM4_法律状态())?(short) 1:0);
                    detailDO.setApplier(dataBO.getM7_申请人());
                    detailDO.setInventor(dataBO.getM8_发明人());
                    detailDO.setAgent(dataBO.getM11_代理机构());
                    detailDO.setAddress(dataBO.getM13_地址());
                    detailDO.setIntroduce(dataBO.getM16_摘要().getBytes("utf-8"));
                    System.out.println("detailDO:" + detailDO.toString());
                    boolean result1 = detailService.createDetail(detailDO);
                    System.out.println("插入结果：" + result1);
                }
            }
            br.close();
            System.out.println("==========" + fileName + "==========OK!");
        }
    }



}
