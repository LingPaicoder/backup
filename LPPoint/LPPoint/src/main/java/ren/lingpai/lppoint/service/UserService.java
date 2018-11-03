package ren.lingpai.lppoint.service;

import ren.lingpai.lpagile.annotation.Service;
import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lpagile.plugin.security.annotation.User;
import ren.lingpai.lppoint.entity.RoleDO;
import ren.lingpai.lppoint.entity.UserDO;
import ren.lingpai.lppoint.entity.User_RoleDO;
import ren.lingpai.lputil.clazz.ClassUtil;
import ren.lingpai.lputil.codec.CodecUtil;

import java.util.Map;

/**
 * Created by lrp on 17-5-11.
 */
@Service
public class UserService {

    /**
     * 注册
     * @param userDO
     * -1:失败
     * -2:用户名已存在
     * @return
     */
    public int register(UserDO userDO){
        UserDO tempUser = getUserByUsername(userDO.getUsername());
        if(null != tempUser){
            return -2;
        }

        try {
            userDO.setPassword(CodecUtil.md5(userDO.getPassword()));
            Map<String, Object> fieldMap = ClassUtil.convertBeanToMap(userDO);

            if (DataBasePart.insertEntity(UserDO.class,fieldMap)){

                User_RoleDO user_roleDO = new User_RoleDO();
                user_roleDO.setRoleId(2);
                user_roleDO.setUserId(getUserByUsername(userDO.getUsername()).getId());
                DataBasePart.insertEntity(User_RoleDO.class,ClassUtil.convertBeanToMap(user_roleDO));

                return 1;
            }

            return -1;
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public UserDO getUserByUsername(String username){
        String sql = "SELECT " +
                " m_id as id , m_username as username , m_password as password " +
                " , m_realname as realName , m_email as email " +
                ", m_createtime as createTime , m_picpath as picPath " +
                " FROM t_user WHERE m_username = ? ";
        return DataBasePart.queryEntity(UserDO.class,sql,username);
    }

}
