package ren.lingpai.lpagiledemo.service;

import ren.lingpai.lpagile.annotation.Service;
import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lpagiledemo.entity.*;
import ren.lingpai.lpagiledemo.model.DetailBO;
import ren.lingpai.lputil.cast.CastUtil;
import ren.lingpai.lputil.clazz.ClassUtil;
import ren.lingpai.lputil.collection.CollectionUtil;

import javax.rmi.CORBA.UtilDelegate;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created on 17-3-5.
 */
@Service
public class DetailService {

    /**
     * 返回专利申请最小年份和最大年份
     * @return
     */
    public Map<String,String> getDetailDateMaxAndMin(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Map<String ,String > result = new HashMap<String, String>();
        List<DetailDO> list = getDetailList();
        if(CollectionUtil.isEmpty(list)) return  null;
        result.put("maxYear", sdf.format(list.get(0).getDate()));
        result.put("minYear", sdf.format(list.get(list.size()-1).getDate()));
        return result;
    }

    /**
     * 返回每年申请量
     * @return
     */
    public Map<String,List<Long>> getNumByYear() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Long> result0 = new ArrayList<Long>();
        Map<String ,String > yearMap = getDetailDateMaxAndMin();
        if (CollectionUtil.isEmpty(yearMap)) return null;
        for (int i = CastUtil.castInt(yearMap.get("minYear"))
             ; i <=  CastUtil.castInt(yearMap.get("maxYear"))
             ; i ++){
            String sql = "SELECT count(*) FROM t_detail where m_date between ? and ? ";
            Long num = DataBasePart.query(sql,
                    new Date(sdf.parse(CastUtil.castString(i) + "-01-01").getTime()),
                    new Date(sdf.parse(CastUtil.castString(i) + "-12-31").getTime()));
            result0.add(num);
        }
        Map<String,List<Long>> result = new HashMap<String, List<Long>>();
        result.put("everyYearAmount",result0);
        return result;
    }

    /**
     * 返回每省的申请量
     * @return
     */
    public Map<String ,List<Map<String ,String>>> getNumByProvence(){
        Map<String ,List<Map<String ,String>>> result = new HashMap<String ,List<Map<String ,String>>>();
        List<ProvenceDO> provenceDOs = getAllProvence();
        if(CollectionUtil.isEmpty(provenceDOs)) return null;
        List<Map<String ,String>> result1 = new ArrayList<Map<String, String>>();
        for (ProvenceDO provenceDO : provenceDOs){
            String sql = "SELECT count(*) FROM t_detail where m_provenceid = ?  ";
            Long num = DataBasePart.query(sql,
                    provenceDO.getProvenceId());
            Map<String , String> result0  = new HashMap<String, String>();
            result0.put("name",provenceDO.getProvenceName());
            result0.put("value",CastUtil.castString(num));
            result1.add(result0);
        }
        result.put("everyProvenceAmount",result1);
        return result;
    }

    /**
     * 根据类别查询专利数量
     * @return
     */
    public List<Map<String,List<String>>> getNumByType(){
        List<Map<String,List<String>>> result = new ArrayList<Map<String, List<String>>>();
        List<TypeDO> typeDOs = getTypeList();
        if(CollectionUtil.isEmpty(typeDOs)) return null;
        //类别
        Map<String,List<String>> resultMap1 = new HashMap<String, List<String>>();
        List<String> resultList1 = new ArrayList<String>();
        for (TypeDO typeDO : typeDOs) {
            resultList1.add(typeDO.getTypeName());
        }
        resultMap1.put("type",resultList1);
        //数量
        Map<String,List<String>> resultMap2 = new HashMap<String, List<String>>();
        List<String> resultList2 = new ArrayList<String>();
        for (TypeDO typeDO : typeDOs){
            String sql = "SELECT count(*) FROM t_detail where m_typeid = ?  ";
            Long num =  (Long)DataBasePart.query(sql,
                    typeDO.getTypeId());
            resultList2.add(CastUtil.castString(num));
        }
        resultMap2.put("amount",resultList2);
        result.add(resultMap1);
        result.add(resultMap2);
        return result;
    }

    /**
     * 返回每个省每个类别的申请量
     * @return
     */
    public Map<String,Object> getNumByProvenceAndType(){
        Map<String,Object> result = new HashMap<String, Object>();
        List<ProvenceDO> provenceDOs = getAllProvence();
        List<TypeDO> typeDOs = getTypeList();
        if(CollectionUtil.isEmpty(provenceDOs) || CollectionUtil.isEmpty(typeDOs)) return null;
        List<String> provenceNames = new ArrayList<String>();
        for(int i = 0 ; i < provenceDOs.size() ; i ++ ){
            provenceNames.add(provenceDOs.get(i).getProvenceName());
        }
        result.put("provence",provenceNames);
        List<String> typeNames = new ArrayList<String>();
        for(int j = 0 ; j < typeDOs.size() ; j ++ ){
            typeNames.add(typeDOs.get(j).getTypeName());
        }
        result.put("type",typeNames);
        long[][] amount = new long[typeDOs.size()][provenceDOs.size()];

        for(int i = 0 ; i < typeDOs.size() ; i ++ ){
            for (int j = 0 ; j < provenceDOs.size() ; j ++){
                String sql = "SELECT count(*) FROM t_detail where m_typeid = ? and m_provenceid = ? ";
                long num = (Long)DataBasePart.query(sql,
                        typeDOs.get(i).getTypeId(),
                        provenceDOs.get(j).getProvenceId());
                amount[i][j] = num;
            }
        }

        result.put("amount",amount);
        return result;
    }

    /**
     * 返回每个省每个年的申请量
     * @return
     */
    public Map<String,Object> getNumByProvenceAndYear() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> result = new HashMap<String, Object>();
        List<ProvenceDO> provenceDOs = getAllProvence();
        if (CollectionUtil.isEmpty(provenceDOs)) return null;
        List<String> provenceNames = new ArrayList<String>();
        for(int i = 0 ; i < provenceDOs.size() ; i ++ ){
            provenceNames.add(provenceDOs.get(i).getProvenceName());
        }
        result.put("provence",provenceNames);
        Map<String ,String > yearMap = getDetailDateMaxAndMin();
        if (CollectionUtil.isEmpty(yearMap)) return null;
        int yearNum = 0;
        for (int j = CastUtil.castInt(yearMap.get("minYear"))
             ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                ; j ++){
            yearNum ++ ;
        }
        int[]  year = new int[yearNum];

        long[][] amount = new long[provenceDOs.size()][yearNum];
        for(int i = 0 ; i < provenceDOs.size() ; i ++ ){
            for (int j = CastUtil.castInt(yearMap.get("minYear"))
                 ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                    ; j ++){
                year[j-CastUtil.castInt(yearMap.get("minYear"))] = j;
                String sql = "SELECT count(*) FROM t_detail where m_provenceid = ? and m_date between ? and ? ";
                Long num = DataBasePart.query(sql,
                        provenceDOs.get(i).getProvenceId(),
                        new Date(sdf.parse(CastUtil.castString(j) + "-01-01").getTime()),
                        new Date(sdf.parse(CastUtil.castString(j) + "-12-31").getTime()));
                amount[i][j-CastUtil.castInt(yearMap.get("minYear"))] = num;
            }
        }
        result.put("year",year);
        result.put("amount",amount);
        return result;
    }

    /**
     * 返回每年每类别的申请量
     * @return
     */
    public Map<String,Object> getNumByTypeAndYear() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> result = new HashMap<String, Object>();

        List<TypeDO> typeDOs = getTypeList();
        if(CollectionUtil.isEmpty(typeDOs)) return null;
        List<String> typeNames = new ArrayList<String>();
        for(int j = 0 ; j < typeDOs.size() ; j ++ ){
            typeNames.add(typeDOs.get(j).getTypeName());
        }
        result.put("type",typeNames);

        Map<String ,String > yearMap = getDetailDateMaxAndMin();
        if (CollectionUtil.isEmpty(yearMap)) return null;
        int yearNum = 0;
        for (int j = CastUtil.castInt(yearMap.get("minYear"))
             ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                ; j ++){
            yearNum ++ ;
        }
        int[]  year = new int[yearNum];

        long[][] amount = new long[typeDOs.size()][yearNum];
        for(int i = 0 ; i < typeDOs.size() ; i ++ ){
            for (int j = CastUtil.castInt(yearMap.get("minYear"))
                 ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                    ; j ++){
                year[j-CastUtil.castInt(yearMap.get("minYear"))] = j;
                String sql = "SELECT count(*) FROM t_detail where m_typeid = ? and m_date between ? and ? ";
                Long num = DataBasePart.query(sql,
                        typeDOs.get(i).getTypeId(),
                        new Date(sdf.parse(CastUtil.castString(j) + "-01-01").getTime()),
                        new Date(sdf.parse(CastUtil.castString(j) + "-12-31").getTime()));
                amount[i][j-CastUtil.castInt(yearMap.get("minYear"))] = num;
            }
        }
        result.put("year",year);
        result.put("amount",amount);
        return result;
    }

    /**
     * 返回每个类别的单位
     * @return
     */
    public Map<String,Object> getUtilByType(){
        Map<String,Object> result = new HashMap<String, Object>();
        List<TypeDO> typeDOs = getTypeList();
        if(CollectionUtil.isEmpty(typeDOs)) return null;
        for(TypeDO typeDO : typeDOs){
            List<UnitDO> unitDOs = getUnitListByTypeId(typeDO.getTypeId());
            Set<String> unitNames = new HashSet<String>();
            if(CollectionUtil.isEmpty(unitDOs)){
                result.put(typeDO.getTypeName(),null);
            }else {
                for (UnitDO unitDO : unitDOs){
                    unitNames.add(unitDO.getUnitName());
                }
                result.put(typeDO.getTypeName(),unitNames);
            }
        }
        return result;
    }

    /**
     * 返回每个单位申请的专利详情
     * @param unitName
     * @return
     */
    public List<DetailDO> getDetailByUnit(String unitName){
        String sql = "SELECT " +
                " m_id as id , m_patentid as patentId , m_date as date , m_provenceid as provenceId , m_cityid as cityId " +
                " , t_detail.m_unitid as unitId , m_typeid as typeId , m_status as status , m_applier as applier " +
                " , m_inventor as inventor , m_agent as agent , m_address as address , m_introduce as introduce " +
                " FROM t_detail " +
                " inner join t_unit on t_unit.m_unitid = t_detail.m_unitid " +
                " where t_unit.m_unitname = ? order by date DESC";
        return DataBasePart.queryEntityList(DetailDO.class, sql,unitName);

    }

    public DetailBO parseDetailDOToBO(DetailDO detailDO) throws Exception{
        DetailBO detailBO = new DetailBO();
        detailBO.setTypeId(detailDO.getTypeId());
        detailBO.setDate(detailDO.getDate());
        detailBO.setAddress(detailDO.getAddress());
        detailBO.setAgent(detailDO.getAgent());
        detailBO.setApplier(detailDO.getApplier());
        detailBO.setCityId(detailDO.getCityId());
        detailBO.setId(detailDO.getId());
        detailBO.setIntroduce(detailDO.getIntroduce());
        detailBO.setInventor(detailDO.getInventor());
        detailBO.setPatentId(detailDO.getPatentId());
        detailBO.setProvenceId(detailDO.getProvenceId());
        detailBO.setStatus(detailDO.getStatus());
        detailBO.setUnitId(detailDO.getUnitId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        detailBO.setDateStr(sdf.format(new java.util.Date(detailDO.getDate().getTime())));
        ProvenceDO provenceDO = new ProvenceDO();
        String sql = "SELECT " +
                " m_provenceid as provenceId , m_provenceName as provenceName " +
                " FROM t_provence where m_provenceid = ? ";
        provenceDO = DataBasePart.queryEntity(ProvenceDO.class, sql,detailDO.getProvenceId());
        if (null != provenceDO) detailBO.setProvenceName(provenceDO.getProvenceName());
        CityDO cityDO = new CityDO();
        String sql2 = "SELECT " +
                " m_id as cityId , m_cityname as cityName " +
                " FROM t_city where m_id = ? ";
        cityDO = DataBasePart.queryEntity(CityDO.class, sql2,detailDO.getCityId());
        if (null != cityDO) detailBO.setCityName(cityDO.getCityName());
        TypeDO typeDO = new TypeDO();
        String sql3 = "SELECT " +
                " m_typeid as typeId , m_typename as typeName " +
                " FROM t_type where m_typeid = ? ";
        typeDO = DataBasePart.queryEntity(TypeDO.class, sql3,detailDO.getTypeId());
        if (null != typeDO) detailBO.setTypeName(typeDO.getTypeName());
        UnitDO unitDO = new UnitDO();
        String sql4 = "SELECT " +
                " m_unitid as unitId , m_unitname as unitName " +
                " FROM t_unit where m_unitid = ? ";
        unitDO = DataBasePart.queryEntity(UnitDO.class, sql4,detailDO.getTypeId());
        if (null != unitDO) detailBO.setUnitName(unitDO.getUnitName());
        return detailBO;
    }

    /**
     * 返回每个类别的推荐类别
     * @param unitName
     * @param typeName
     * @return
     */
    public String getRecommendType(String unitName,String typeName){
        String result = null;
        TypeDO typeDO = new TypeDO();
        String sql = "SELECT " +
                " m_typeid as typeId , m_typename as typeName " +
                " FROM t_type where m_typename = ? ";
        typeDO = DataBasePart.queryEntity(TypeDO.class, sql,typeName);

        if (null == typeDO) return null;

        Map<String,Integer> typeMap = new HashMap<String, Integer>();
        List<UnitDO> unitDOs = getUnitListByTypeId(typeDO.getTypeId());
        for (UnitDO unitDO : unitDOs){
            List<TypeDO> typeDOs = getTypeListByUnitId(unitDO.getUnitId());
            for(TypeDO type : typeDOs){
                Integer num = typeMap.get(type.getTypeName());
                if((null == num || num <= 0) && (type.getTypeId() != typeDO.getTypeId())){
                    num = 1;
                    typeMap.put(type.getTypeName(),num);
                }else if (type.getTypeId() != typeDO.getTypeId()){
                    num += 1;
                    typeMap.put(type.getTypeName(),num);
                }
            }
        }

        int maxNum = 0;
        for (Map.Entry<String,Integer> entry : typeMap.entrySet()){
            if(entry.getValue() > maxNum){
                maxNum = entry.getValue();
                result = entry.getKey();
            }
        }
        return result;
    }

    /**
     * 每类别每年的申请质量
     * @return
     */
    public Map<String,Object> getQualityNumByTypeAndYear() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> result = new HashMap<String, Object>();

        List<TypeDO> typeDOs = getTypeList();
        if(CollectionUtil.isEmpty(typeDOs)) return null;
        List<String> typeNames = new ArrayList<String>();
        for(int j = 0 ; j < typeDOs.size() ; j ++ ){
            typeNames.add(typeDOs.get(j).getTypeName());
        }
        result.put("type",typeNames);

        Map<String ,String > yearMap = getDetailDateMaxAndMin();
        if (CollectionUtil.isEmpty(yearMap)) return null;
        int yearNum = 0;
        for (int j = CastUtil.castInt(yearMap.get("minYear"))
             ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                ; j ++){
            yearNum ++ ;
        }
        int[]  year = new int[yearNum];

        long[][] valid = new long[typeDOs.size()][yearNum];
        for(int i = 0 ; i < typeDOs.size() ; i ++ ){
            for (int j = CastUtil.castInt(yearMap.get("minYear"))
                 ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                    ; j ++){
                year[j-CastUtil.castInt(yearMap.get("minYear"))] = j;
                String sql = "SELECT count(*) FROM t_detail where m_status > 0 and m_typeid = ? and m_date between ? and ? ";
                Long num = DataBasePart.query(sql,
                        typeDOs.get(i).getTypeId(),
                        new Date(sdf.parse(CastUtil.castString(j) + "-01-01").getTime()),
                        new Date(sdf.parse(CastUtil.castString(j) + "-12-31").getTime()));
                valid[i][j-CastUtil.castInt(yearMap.get("minYear"))] = num;
            }
        }

        long[][] invalid = new long[typeDOs.size()][yearNum];
        for(int i = 0 ; i < typeDOs.size() ; i ++ ){
            for (int j = CastUtil.castInt(yearMap.get("minYear"))
                 ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                    ; j ++){
                year[j-CastUtil.castInt(yearMap.get("minYear"))] = j;
                String sql = "SELECT count(*) FROM t_detail where m_status <= 0 and m_typeid = ? and m_date between ? and ? ";
                Long num = DataBasePart.query(sql,
                        typeDOs.get(i).getTypeId(),
                        new Date(sdf.parse(CastUtil.castString(j) + "-01-01").getTime()),
                        new Date(sdf.parse(CastUtil.castString(j) + "-12-31").getTime()));
                invalid[i][j-CastUtil.castInt(yearMap.get("minYear"))] = num;
            }
        }
        result.put("year",year);
        result.put("valid",valid);
        result.put("invalid",invalid);
        return result;
    }

    /**
     * 每省每年的申请质量
     * @return
     */
    public Map<String,Object> getQualityNumByProvenceAndYear() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> result = new HashMap<String, Object>();
        List<ProvenceDO> provenceDOs = getAllProvence();
        if (CollectionUtil.isEmpty(provenceDOs)) return null;
        List<String> provenceNames = new ArrayList<String>();
        for(int i = 0 ; i < provenceDOs.size() ; i ++ ){
            provenceNames.add(provenceDOs.get(i).getProvenceName());
        }
        result.put("provence",provenceNames);
        Map<String ,String > yearMap = getDetailDateMaxAndMin();
        if (CollectionUtil.isEmpty(yearMap)) return null;
        int yearNum = 0;
        for (int j = CastUtil.castInt(yearMap.get("minYear"))
             ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                ; j ++){
            yearNum ++ ;
        }
        int[]  year = new int[yearNum];

        long[][] valid = new long[provenceDOs.size()][yearNum];
        for(int i = 0 ; i < provenceDOs.size() ; i ++ ){
            for (int j = CastUtil.castInt(yearMap.get("minYear"))
                 ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                    ; j ++){
                year[j-CastUtil.castInt(yearMap.get("minYear"))] = j;
                String sql = "SELECT count(*) FROM t_detail where m_status > 0 and m_provenceid = ? and m_date between ? and ? ";
                Long num = DataBasePart.query(sql,
                        provenceDOs.get(i).getProvenceId(),
                        new Date(sdf.parse(CastUtil.castString(j) + "-01-01").getTime()),
                        new Date(sdf.parse(CastUtil.castString(j) + "-12-31").getTime()));
                valid[i][j-CastUtil.castInt(yearMap.get("minYear"))] = num;
            }
        }

        long[][] invalid = new long[provenceDOs.size()][yearNum];
        for(int i = 0 ; i < provenceDOs.size() ; i ++ ){
            for (int j = CastUtil.castInt(yearMap.get("minYear"))
                 ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                    ; j ++){
                year[j-CastUtil.castInt(yearMap.get("minYear"))] = j;
                String sql = "SELECT count(*) FROM t_detail where m_status <= 0 and m_provenceid = ? and m_date between ? and ? ";
                Long num = DataBasePart.query(sql,
                        provenceDOs.get(i).getProvenceId(),
                        new Date(sdf.parse(CastUtil.castString(j) + "-01-01").getTime()),
                        new Date(sdf.parse(CastUtil.castString(j) + "-12-31").getTime()));
                invalid[i][j-CastUtil.castInt(yearMap.get("minYear"))] = num;
            }
        }
        result.put("year",year);
        result.put("valid",valid);
        result.put("invalid",invalid);
        return result;
    }

    /**
     * 每年申请质量
     * @return
     */
    public Map<String,Object> getQualityNumByYear() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Map<String ,String > yearMap = getDetailDateMaxAndMin();
        if (CollectionUtil.isEmpty(yearMap)) return null;
        int yearNum = 0;
        for (int j = CastUtil.castInt(yearMap.get("minYear"))
             ; j <=  CastUtil.castInt(yearMap.get("maxYear"))
                ; j ++){
            yearNum ++ ;
        }
        int[]  year = new int[yearNum];

        long[] valid = new long[yearNum];
        long[] invalid = new long[yearNum];

        for (int i = CastUtil.castInt(yearMap.get("minYear"))
             ; i <=  CastUtil.castInt(yearMap.get("maxYear"))
                ; i ++){
            String sql = "SELECT count(*) FROM t_detail where m_status > 0 and m_date between ? and ? ";
            Long num = DataBasePart.query(sql,
                    new Date(sdf.parse(CastUtil.castString(i) + "-01-01").getTime()),
                    new Date(sdf.parse(CastUtil.castString(i) + "-12-31").getTime()));
            valid[i-CastUtil.castInt(yearMap.get("minYear"))] = num;
        }

        for (int i = CastUtil.castInt(yearMap.get("minYear"))
             ; i <=  CastUtil.castInt(yearMap.get("maxYear"))
                ; i ++){
            String sql = "SELECT count(*) FROM t_detail where m_status <= 0 and m_date between ? and ? ";
            Long num = DataBasePart.query(sql,
                    new Date(sdf.parse(CastUtil.castString(i) + "-01-01").getTime()),
                    new Date(sdf.parse(CastUtil.castString(i) + "-12-31").getTime()));
            invalid[i-CastUtil.castInt(yearMap.get("minYear"))] = num;
        }

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("year",year);
        result.put("valid",valid);
        result.put("invalid",invalid);
        return result;
    }

    /**
     * 根据类别查询专利数量
     * @return
     */
    public Map<String,Object> getQualityNumByType(){
        Map<String,Object> result = new HashMap<String, Object>();

        List<TypeDO> typeDOs = getTypeList();
        if(CollectionUtil.isEmpty(typeDOs)) return null;
        List<String> typeNames = new ArrayList<String>();
        for(int j = 0 ; j < typeDOs.size() ; j ++ ){
            typeNames.add(typeDOs.get(j).getTypeName());
        }
        result.put("type",typeNames);

        long[] valid = new long[typeDOs.size()];
        long[] invalid = new long[typeDOs.size()];

        //数量
        for (int i = 0 ; i < typeDOs.size() ; i ++){
            TypeDO typeDO = typeDOs.get(i);
            String sql = "SELECT count(*) FROM t_detail where m_status > 0 and m_typeid = ?  ";
            Long num =  (Long)DataBasePart.query(sql,
                    typeDO.getTypeId());
            valid[i] = num;
        }
        for (int i = 0 ; i < typeDOs.size() ; i ++){
            TypeDO typeDO = typeDOs.get(i);
            String sql = "SELECT count(*) FROM t_detail where m_status <= 0 and m_typeid = ?  ";
            Long num =  (Long)DataBasePart.query(sql,
                    typeDO.getTypeId());
            invalid[i] = num;
        }
        result.put("valid",valid);
        result.put("invalid",invalid);
        return result;
    }

    /**
     * 每省的申请质量
     * @return
     */
    public Map<String,Object> getQualityNumByProvence() {
        Map<String, Object> result = new HashMap<String, Object>();

        List<ProvenceDO> provenceDOs = getAllProvence();
        if (CollectionUtil.isEmpty(provenceDOs)) return null;
        List<String> provenceNames = new ArrayList<String>();
        for(int i = 0 ; i < provenceDOs.size() ; i ++ ){
            provenceNames.add(provenceDOs.get(i).getProvenceName());
        }
        result.put("provence",provenceNames);

        long[] valid = new long[provenceDOs.size()];
        long[] invalid = new long[provenceDOs.size()];

        for (int i = 0 ; i < provenceDOs.size() ; i ++) {
            ProvenceDO provenceDO = provenceDOs.get(i);
            String sql = "SELECT count(*) FROM t_detail where m_status > 0 and m_provenceid = ?  ";
            Long num = DataBasePart.query(sql,
                    provenceDO.getProvenceId());
            valid[i] = num;
        }
        for (int i = 0 ; i < provenceDOs.size() ; i ++) {
            ProvenceDO provenceDO = provenceDOs.get(i);
            String sql = "SELECT count(*) FROM t_detail where m_status <= 0 and m_provenceid = ?  ";
            Long num = DataBasePart.query(sql,
                    provenceDO.getProvenceId());
            invalid[i] = num;
        }
        result.put("valid",valid);
        result.put("invalid",invalid);
        return result;
    }


    /**
     * 添加省份
     * @param provenceName
     * @return
     */
    public boolean createProvence(String provenceName){
        boolean result = false;
        try {
            ProvenceDO provenceDO = new ProvenceDO();
            provenceDO.setProvenceName(provenceName);
            result = DataBasePart.insertEntity(ProvenceDO.class, ClassUtil.convertBeanToMap(provenceDO));
        }catch (Exception e){
            result = false;
        }
        return result;
    }

    /**
     * 查询省份
     * @return
     */
    public ProvenceDO getProvence(String provenceName){
        String sql = "SELECT " +
                " m_provenceid as provenceId , m_provenceName as provenceName " +
                " FROM t_provence where m_provenceName = ? ";
        return DataBasePart.queryEntity(ProvenceDO.class, sql,provenceName);
    }

    /**
     * 获取所有省份列表
     * @return
     */
    public List<ProvenceDO> getAllProvence(){
        String sql = "SELECT " +
                " m_provenceid as provenceId , m_provenceName as provenceName " +
                " FROM t_provence ";
        return DataBasePart.queryEntityList(ProvenceDO.class, sql);
    }

    /**
     * 获取所有城市列表
     * @return
     */
    public List<CityDO> getAllCity(){
        String sql = "SELECT " +
                " m_id as id , m_cityname as cityName , m_provenceid as provenceId" +
                " FROM t_city ";
        return DataBasePart.queryEntityList(CityDO.class, sql);
    }

    /**
     * 添加类别
     * @param typeName
     * @return
     */
    public boolean createType(String typeName){
        boolean result = false;
        try {
            TypeDO typeDO = new TypeDO();
            typeDO.setTypeName(typeName);
            result = DataBasePart.insertEntity(TypeDO.class, ClassUtil.convertBeanToMap(typeDO));
        }catch (Exception e){
            result = false;
        }
        return result;
    }

    /**
     * 查询类别
     * @return
     */
    public TypeDO getType(String typeName){
        String sql = "SELECT " +
                " m_typeid as typeId , m_typename as typeName " +
                " FROM t_type where m_typename = ? ";
        return DataBasePart.queryEntity(TypeDO.class, sql,typeName);
    }

    /**
     * 获取所有种类列表
     * @return
     */
    public List<TypeDO> getTypeList(){
        String sql = "SELECT " +
                " m_typeid as typeId , m_typename as typeName " +
                " FROM t_type ";
        return DataBasePart.queryEntityList(TypeDO.class, sql);
    }

    /**
     * 添加单位
     * @param unitName
     * @return
     */
    public boolean createUnit(String unitName){
        boolean result = false;
        try {
            UnitDO unitDO = new UnitDO();
            unitDO.setUnitName(unitName);
            result = DataBasePart.insertEntity(UnitDO.class, ClassUtil.convertBeanToMap(unitDO));
        }catch (Exception e){
            result = false;
        }
        return result;
    }

    /**
     * 查询单位
     * @return
     */
    public UnitDO getUnit(String unitName){
        String sql = "SELECT " +
                " m_unitid as unitId , m_unitname as unitName " +
                " FROM t_unit where m_unitname = ? ";
        return DataBasePart.queryEntity(UnitDO.class, sql,unitName);
    }

    /**
     * 获取所有单位列表
     * @return
     */
    public List<UnitDO> getUnitList(){
        String sql = "SELECT " +
                " m_unitid as unitId , m_unitname as unitName " +
                " FROM t_unit ";
        return DataBasePart.queryEntityList(UnitDO.class, sql);
    }

    /**
     * 根据typeid查询单位
     * @param typeId
     * @return
     */
    public List<UnitDO> getUnitListByTypeId(int typeId){
        String sql = "SELECT " +
                " t_unit.m_unitid as unitId , m_unitname as unitName " +
                " FROM (t_type_unit " +
                " inner join t_unit on t_type_unit.m_unitid = t_unit.m_unitid) " +
                " inner join t_type on t_type_unit.m_typeid = t_type.m_typeid " +
                " where t_type_unit.m_typeid = ? ";
        return DataBasePart.queryEntityList(UnitDO.class, sql,typeId);
    }

    /**
     * 根据unitid查询类别
     * @param unitId
     * @return
     */
    public List<TypeDO> getTypeListByUnitId(int unitId){
        String sql = "SELECT " +
                " t_type.m_typeid as typeId , m_typename as typeName " +
                " FROM (t_type_unit " +
                " inner join t_unit on t_type_unit.m_unitid = t_unit.m_unitid) " +
                " inner join t_type on t_type_unit.m_typeid = t_type.m_typeid " +
                " where t_type_unit.m_unitid = ? ";
        return DataBasePart.queryEntityList(TypeDO.class, sql,unitId);
    }


    /**
     * 获取所有专利列表
     * @return
     */
    public List<DetailDO> getDetailList(){
        String sql = "SELECT " +
                " m_id as id , m_patentid as patentId , m_date as date , m_cityid as cityId " +
                " , m_unitid as unitId , m_status as status , m_applier as applier " +
                " , m_inventor as inventor , m_agent as agent , m_address as address , m_introduce as introduce " +
                " FROM t_detail order by date DESC";
        return DataBasePart.queryEntityList(DetailDO.class, sql);
    }

    /**
     * 添加专利
     * @param detailDO
     * @return
     */
    public boolean createDetail(DetailDO detailDO){
        boolean result = false;
        try {
            result = DataBasePart.insertEntity(DetailDO.class, ClassUtil.convertBeanToMap(detailDO));
        }catch (Exception e){
            result = false;
        }
        return result;
    }

    /**
     * 添加类别和单位的关系
     * @param type_unitDO
     * @return
     */
    public boolean createType_Unit(Type_UnitDO type_unitDO){
        boolean result = false;
        try {
            result = DataBasePart.insertEntity(Type_UnitDO.class, ClassUtil.convertBeanToMap(type_unitDO));
        }catch (Exception e){
            result = false;
        }
        return result;
    }



    //=====================================================


    /**
     * 获取文章
     * @param id
     * @return
     */
    public DetailDO getArticle(int id){
        String sql = "SELECT " +
                " m_id as id , m_title as title , m_typeid as typeId , m_creator as creator " +
                " , m_createtime as createTime , m_summary as summary , m_coverimg as coverImg " +
                " , m_articlecontent as articleContent , m_readnum as readNum , m_upnum as upNum , m_sort as sort " +
                " FROM t_article WHERE m_id = ?";
        return DataBasePart.queryEntity(DetailDO.class,sql,id);
    }

    /**
     * 创建客户
     * @param fieldMap
     * @return
     */
    public boolean createArticle(Map<String,Object> fieldMap){
        return DataBasePart.insertEntity(DetailDO.class,fieldMap);
    }

    /**
     * 更新客户
     * @param id
     * @param fieldMap
     * @return
     */
    public boolean updateArticle(int id,Map<String,Object> fieldMap){
        return DataBasePart.updateEntity(DetailDO.class,id,fieldMap);
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    public boolean deleteArticle(int id){
        return DataBasePart.deleteEntity(DetailDO.class,id);
    }
    
}
