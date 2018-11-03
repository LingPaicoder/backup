package ren.lingpai.lppoint.service;

import ren.lingpai.lpagile.annotation.Service;
import ren.lingpai.lpagile.part.DataBasePart;
import ren.lingpai.lppoint.entity.MessageDO;
import ren.lingpai.lppoint.model.MessageBO;
import ren.lingpai.lppoint.util.PageParam;
import ren.lingpai.lputil.cast.CastUtil;
import ren.lingpai.lputil.clazz.ClassUtil;
import ren.lingpai.lputil.collection.CollectionUtil;

import java.util.List;

/**
 * Created by lrp on 17-5-15.
 */
@Service
public class MessageService {

    /**
     * 添加留言
     * @param messageDO
     * @return
     */
    public boolean createMessage(MessageDO messageDO){
        boolean result = false;
        try {
            result = DataBasePart.insertEntity(MessageDO.class, ClassUtil.convertBeanToMap(messageDO));
        }catch (Exception e){
            result = false;
        }
        return result;
    }

    /**
     * 获取留言
     * @param id
     * @return
     */
    public MessageDO getMessage(int id){
        String sql = "SELECT " +
                " m_id as id , m_userid as userId , m_content as content " +
                " , m_createtime as createTime , m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                " FROM t_message WHERE m_id = ?";
        return DataBasePart.queryEntity(MessageDO.class,sql,id);
    }

    /**
     * 获取Type列表
     * @return
     */
    public List<MessageBO> getMessageList(){
        //获取所有第一级留言
        String sql = "SELECT " +
                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                " FROM t_message " +
                " inner join t_user on t_message.m_userid = t_user.m_id " +
                " where m_pmsgid = -1 order by id DESC ";
        List<MessageBO> messageBOList = DataBasePart.queryEntityList(MessageBO.class, sql);

        //获取第二级留言
        if (CollectionUtil.isNotEmpty(messageBOList)){
            for (MessageBO messageBO : messageBOList){
                String sql2 = "SELECT " +
                        " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                        " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                        " FROM t_message " +
                        " inner join t_user on t_message.m_userid = t_user.m_id " +
                        " where m_pmsgid = ? order by id DESC ";
                List<MessageBO> messageBO2List = DataBasePart.queryEntityList(MessageBO.class, sql2 , messageBO.getId());
                messageBO.setSubMessages(messageBO2List);

                //获取第三级留言
                if(CollectionUtil.isNotEmpty(messageBO2List)){
                    for (MessageBO messageBO2 : messageBO2List){
                        String sql3 = "SELECT " +
                                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                                " FROM t_message " +
                                " inner join t_user on t_message.m_userid = t_user.m_id " +
                                " where m_pmsgid = ? order by id DESC ";
                        List<MessageBO> messageBO3List = DataBasePart.queryEntityList(MessageBO.class, sql3 , messageBO2.getId());
                        messageBO2.setSubMessages(messageBO3List);

                        //获取第四级留言
                        if(CollectionUtil.isNotEmpty(messageBO3List)){
                            for (MessageBO messageBO3 : messageBO3List){
                                String sql4 = "SELECT " +
                                        " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                        " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                                        " FROM t_message " +
                                        " inner join t_user on t_message.m_userid = t_user.m_id " +
                                        " where m_pmsgid = ? order by id DESC ";
                                List<MessageBO> messageBO4List = DataBasePart.queryEntityList(MessageBO.class, sql4 , messageBO3.getId());
                                messageBO3.setSubMessages(messageBO4List);

                                //获取第五级留言
                                if(CollectionUtil.isNotEmpty(messageBO4List)){
                                    for (MessageBO messageBO4 : messageBO4List) {
                                        String sql5 = "SELECT " +
                                                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                                " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                                                " FROM t_message " +
                                                " inner join t_user on t_message.m_userid = t_user.m_id " +
                                                " where m_pmsgid = ? order by id DESC ";
                                        List<MessageBO> messageBO5List = DataBasePart.queryEntityList(MessageBO.class, sql5, messageBO4.getId());
                                        messageBO4.setSubMessages(messageBO5List);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return messageBOList;
    }

    /**
     * 分页获取Type列表
     * @return
     */
    public List<MessageBO> getMessageListByPage(PageParam pageParam){

        String sqlCount = "SELECT count(*) " +
                " FROM t_message where m_pmsgid = -1 and m_articleid = 0  order by m_id DESC ";
        Long totalCount = DataBasePart.query(sqlCount);
        PageParam.setPageValues(CastUtil.castInt(totalCount),pageParam);

        //获取所有第一级留言
        String sql = "SELECT " +
                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                " FROM t_message " +
                " inner join t_user on t_message.m_userid = t_user.m_id " +
                " where m_pmsgid = -1 and m_articleid = 0 order by id DESC " +
                " limit " + (pageParam.getCurrentPage()-1)*pageParam.getPageSize() + " , " + pageParam.getPageSize();;
        List<MessageBO> messageBOList = DataBasePart.queryEntityList(MessageBO.class, sql);

        //获取第二级留言
        if (CollectionUtil.isNotEmpty(messageBOList)){
            for (MessageBO messageBO : messageBOList){
                String sql2 = "SELECT " +
                        " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                        " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                        " FROM t_message " +
                        " inner join t_user on t_message.m_userid = t_user.m_id " +
                        " where m_pmsgid = ? order by id DESC ";
                List<MessageBO> messageBO2List = DataBasePart.queryEntityList(MessageBO.class, sql2 , messageBO.getId());
                messageBO.setSubMessages(messageBO2List);

                //获取第三级留言
                if(CollectionUtil.isNotEmpty(messageBO2List)){
                    for (MessageBO messageBO2 : messageBO2List){
                        String sql3 = "SELECT " +
                                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                                " FROM t_message " +
                                " inner join t_user on t_message.m_userid = t_user.m_id " +
                                " where m_pmsgid = ? order by id DESC ";
                        List<MessageBO> messageBO3List = DataBasePart.queryEntityList(MessageBO.class, sql3 , messageBO2.getId());
                        messageBO2.setSubMessages(messageBO3List);

                        //获取第四级留言
                        if(CollectionUtil.isNotEmpty(messageBO3List)){
                            for (MessageBO messageBO3 : messageBO3List){
                                String sql4 = "SELECT " +
                                        " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                        " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                                        " FROM t_message " +
                                        " inner join t_user on t_message.m_userid = t_user.m_id " +
                                        " where m_pmsgid = ? order by id DESC ";
                                List<MessageBO> messageBO4List = DataBasePart.queryEntityList(MessageBO.class, sql4 , messageBO3.getId());
                                messageBO3.setSubMessages(messageBO4List);

                                //获取第五级留言
                                if(CollectionUtil.isNotEmpty(messageBO4List)){
                                    for (MessageBO messageBO4 : messageBO4List) {
                                        String sql5 = "SELECT " +
                                                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                                " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                                                " FROM t_message " +
                                                " inner join t_user on t_message.m_userid = t_user.m_id " +
                                                " where m_pmsgid = ? order by id DESC ";
                                        List<MessageBO> messageBO5List = DataBasePart.queryEntityList(MessageBO.class, sql5, messageBO4.getId());
                                        messageBO4.setSubMessages(messageBO5List);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return messageBOList;
    }

    /**
     * 分页获取Type列表
     * @return
     */
    public List<MessageBO> getMessageListByPageAndArticle(PageParam pageParam,int articleId){

        if (articleId <= 0) return null;

        String sqlCount = "SELECT count(*) " +
                " FROM t_message where m_pmsgid = -1 and m_articleid = ? order by m_id DESC ";
        Long totalCount = DataBasePart.query(sqlCount,articleId);
        PageParam.setPageValues(CastUtil.castInt(totalCount),pageParam);

        //获取所有第一级留言
        String sql = "SELECT " +
                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                " FROM t_message " +
                " inner join t_user on t_message.m_userid = t_user.m_id " +
                " where m_pmsgid = -1 and m_articleid = ? order by id DESC " +
                " limit " + (pageParam.getCurrentPage()-1)*pageParam.getPageSize() + " , " + pageParam.getPageSize();;
        List<MessageBO> messageBOList = DataBasePart.queryEntityList(MessageBO.class, sql , articleId);

        //获取第二级留言
        if (CollectionUtil.isNotEmpty(messageBOList)){
            for (MessageBO messageBO : messageBOList){
                String sql2 = "SELECT " +
                        " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                        " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                        " FROM t_message " +
                        " inner join t_user on t_message.m_userid = t_user.m_id " +
                        " where m_pmsgid = ? order by id DESC ";
                List<MessageBO> messageBO2List = DataBasePart.queryEntityList(MessageBO.class, sql2 , messageBO.getId());
                messageBO.setSubMessages(messageBO2List);

                //获取第三级留言
                if(CollectionUtil.isNotEmpty(messageBO2List)){
                    for (MessageBO messageBO2 : messageBO2List){
                        String sql3 = "SELECT " +
                                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                                " FROM t_message " +
                                " inner join t_user on t_message.m_userid = t_user.m_id " +
                                " where m_pmsgid = ? order by id DESC ";
                        List<MessageBO> messageBO3List = DataBasePart.queryEntityList(MessageBO.class, sql3 , messageBO2.getId());
                        messageBO2.setSubMessages(messageBO3List);

                        //获取第四级留言
                        if(CollectionUtil.isNotEmpty(messageBO3List)){
                            for (MessageBO messageBO3 : messageBO3List){
                                String sql4 = "SELECT " +
                                        " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                        " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                                        " FROM t_message " +
                                        " inner join t_user on t_message.m_userid = t_user.m_id " +
                                        " where m_pmsgid = ? order by id DESC ";
                                List<MessageBO> messageBO4List = DataBasePart.queryEntityList(MessageBO.class, sql4 , messageBO3.getId());
                                messageBO3.setSubMessages(messageBO4List);

                                //获取第五级留言
                                if(CollectionUtil.isNotEmpty(messageBO4List)){
                                    for (MessageBO messageBO4 : messageBO4List) {
                                        String sql5 = "SELECT " +
                                                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                                " m_pmsgid as pMsgId , m_level as level , m_picpath as picPath , m_username as username , m_realname as realName , m_articleid as articleId " +
                                                " FROM t_message " +
                                                " inner join t_user on t_message.m_userid = t_user.m_id " +
                                                " where m_pmsgid = ? order by id DESC ";
                                        List<MessageBO> messageBO5List = DataBasePart.queryEntityList(MessageBO.class, sql5, messageBO4.getId());
                                        messageBO4.setSubMessages(messageBO5List);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return messageBOList;
    }

    /**
     * 删除留言
     * @param id
     * @return
     */
    public boolean deleteMessage(int id){

        boolean result = false;

        //获取第一级留言
        String sql = "SELECT " +
                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                " m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                " FROM t_message " +
                " where t_message.m_id = ? order by id DESC ";
        List<MessageBO> messageBOList = DataBasePart.queryEntityList(MessageBO.class, sql ,id);

        //获取第二级留言
        if (CollectionUtil.isNotEmpty(messageBOList)){
            for (MessageBO messageBO : messageBOList){
                String sql2 = "SELECT " +
                        " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                        " m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                        " FROM t_message " +
                        " where m_pmsgid = ? order by id DESC ";
                List<MessageBO> messageBO2List = DataBasePart.queryEntityList(MessageBO.class, sql2 , messageBO.getId());
                messageBO.setSubMessages(messageBO2List);

                //获取第三级留言
                if(CollectionUtil.isNotEmpty(messageBO2List)){
                    for (MessageBO messageBO2 : messageBO2List){
                        String sql3 = "SELECT " +
                                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                " m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                                " FROM t_message " +
                                " where m_pmsgid = ? order by id DESC ";
                        List<MessageBO> messageBO3List = DataBasePart.queryEntityList(MessageBO.class, sql3 , messageBO2.getId());
                        messageBO2.setSubMessages(messageBO3List);

                        //获取第四级留言
                        if(CollectionUtil.isNotEmpty(messageBO3List)){
                            for (MessageBO messageBO3 : messageBO3List){
                                String sql4 = "SELECT " +
                                        " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                        " m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                                        " FROM t_message " +
                                        " where m_pmsgid = ? order by id DESC ";
                                List<MessageBO> messageBO4List = DataBasePart.queryEntityList(MessageBO.class, sql4 , messageBO3.getId());
                                messageBO3.setSubMessages(messageBO4List);

                                //获取第五级留言
                                if(CollectionUtil.isNotEmpty(messageBO4List)){
                                    for (MessageBO messageBO4 : messageBO4List) {
                                        String sql5 = "SELECT " +
                                                " t_message.m_id as id , m_userid as userId , m_content as content , t_message.m_createtime as createTime , " +
                                                " m_pmsgid as pMsgId , m_level as level , m_articleid as articleId " +
                                                " FROM t_message " +
                                                " where m_pmsgid = ? order by id DESC ";
                                        List<MessageBO> messageBO5List = DataBasePart.queryEntityList(MessageBO.class, sql5, messageBO4.getId());
                                        messageBO4.setSubMessages(messageBO5List);

                                        if(CollectionUtil.isNotEmpty(messageBO5List)){
                                            for (MessageBO messageBO5 : messageBO5List){
                                                DataBasePart.deleteEntity(MessageDO.class,messageBO5.getId());
                                            }
                                        }

                                    }
                                }

                                if(CollectionUtil.isNotEmpty(messageBO4List)){
                                    for (MessageBO messageBO4 : messageBO4List){
                                        DataBasePart.deleteEntity(MessageDO.class,messageBO4.getId());
                                    }
                                }

                            }
                        }

                        if(CollectionUtil.isNotEmpty(messageBO3List)){
                            for (MessageBO messageBO3 : messageBO3List){
                                DataBasePart.deleteEntity(MessageDO.class,messageBO3.getId());
                            }
                        }

                    }
                }

                if(CollectionUtil.isNotEmpty(messageBO2List)){
                    for (MessageBO messageBO2 : messageBO2List){
                        DataBasePart.deleteEntity(MessageDO.class,messageBO2.getId());
                    }
                }

            }
        }

        if(CollectionUtil.isNotEmpty(messageBOList)){
            for (MessageBO messageBO : messageBOList){
                result = DataBasePart.deleteEntity(MessageDO.class,messageBO.getId());
            }
        }

        return result;
    }

}
