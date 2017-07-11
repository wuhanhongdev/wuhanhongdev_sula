package com.sula.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.MessageService;
import com.sula.service.impl.MessageServiceImpl;
import com.sula.util.ResultJson;
import com.sula.util.Status;
import com.sula.util.Verify;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 消息Controller
 */
public class MessageController extends Controller {
    private MessageService messageService = new MessageServiceImpl();
    /**
     * 获取个人信息
     */
    public void loadMessages(){
        ResultJson json = new ResultJson();
        Integer userId = getParaToInt("userId");
        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(userId == null){
            json.setCode(Status.fail);
            json.setMessage("用户ID不能为空");
        }else{
            if(Verify.isMac(sign)){
                List<Record> records = messageService.queryAllMessage(userId);
                json.setCode(Status.success);
                json.setMessage("消息查询成功");
                json.setResult(records);
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }
}
