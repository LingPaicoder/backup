package ren.lingpai.lppoint.service;

import ren.lingpai.lpagile.annotation.Service;
import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lppoint.entity.TypeDO;

import java.util.List;

/**
 * Created by lrp on 17-5-14.
 */
@Service
public class TypeService {

    /**
     * 获取Type列表
     * @return
     */
    public List<TypeDO> getTypeList(){
        String sql = "SELECT " +
                " m_id as id , m_pid as pId , m_topic as topic , m_direction as direction " +
                " , m_expanded as expanded " +
                " FROM t_type where m_id > 1 ";
        return DataBasePart.queryEntityList(TypeDO.class, sql);
    }

}
