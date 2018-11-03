package ren.lingpai.lppoint.controller.visitor;

import ren.lingpai.lpagile.annotation.*;
import ren.lingpai.lpagile.entity.LPData;
import ren.lingpai.lpagile.entity.LPView;
import ren.lingpai.lpagile.part.ContextPart;
import ren.lingpai.lppoint.entity.MessageDO;
import ren.lingpai.lppoint.entity.UserDO;
import ren.lingpai.lppoint.model.MessageBO;
import ren.lingpai.lppoint.service.MessageService;
import ren.lingpai.lppoint.util.PageParam;

import java.sql.Date;
import java.util.List;

/**
 * Created by lrp on 17-5-14.
 */
@Controller("/visitor_message")
public class MessageController {

    @Inject
    private MessageService messageService;

    /**
     * 进入留言页面
     * @param focusid 获得焦点的留言
     * @return
     */
    @Get
    @Action("/message_view")
    public LPView message(Integer focusid , Integer currentPage){
        currentPage = (currentPage!=null)?currentPage:1;
        PageParam pageParam = PageParam.NewInstance(currentPage);
        List<MessageBO> messageBOList = messageService.getMessageListByPage(pageParam);
        return new LPView("visitor/message.jsp")
                .addModel("messageBOList",messageBOList)
                .addModel("focusid",focusid)
                .addModel("pager",pageParam);
    }

    /**
     * 留言
     * @return
     */
    @Post
    @Action("/message_add")
    public LPData addMessage(MessageDO messageDO){
        String result = "";
        UserDO userDO = ContextPart.getSessionAttribute("currentUser");
        if(null == userDO || (userDO.getId() <= 0)){
            result = "-1:您尚未登录，请登录后重试！";
            return new LPData(result);
        }
        if (-1 == messageDO.getPMsgId()){
            messageDO.setLevel(1);
        }else {
            MessageDO pMessage = messageService.getMessage(messageDO.getPMsgId());
            messageDO.setLevel(pMessage.getLevel() + 1);
        }
        messageDO.setUserId(userDO.getId());
        messageDO.setCreateTime(new Date(new java.util.Date().getTime()));
        if (messageService.createMessage(messageDO)){
            result = "1:成功";
        }else {
            result = "-1:失败";
        }
        return new LPData(result);
    }

    /**
     * 删除留言
     * @return
     */
    @Post
    @Action("/message_delete")
    public LPData deleteMessage(Integer id){
        String result = "";
        UserDO userDO = ContextPart.getSessionAttribute("currentUser");
        MessageDO messageDO = messageService.getMessage(id);
        if(null == userDO || (userDO.getId() <= 0)){
            result = "-1:您尚未登录，请登录后重试！";
            return new LPData(result);
        }
        if(null == messageDO || (messageDO.getId() <= 0)){
            result = "-2:该消息不存在！";
            return new LPData(result);
        }
        if(messageDO.getUserId() != userDO.getId()){
            result = "-3:该消息不是您所创建或登录信息已过期，请重新登录！";
            return new LPData(result);
        }
        if (messageService.deleteMessage(id)){
            result = "1:" + messageDO.getPMsgId();
        }else {
            result = "-1:失败";
        }
        return new LPData(result);
    }

}
