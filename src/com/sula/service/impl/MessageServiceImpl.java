package com.sula.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.MessageService;

import java.util.List;

public class MessageServiceImpl implements MessageService {
    @Override
    public List<Record> queryAllMessage(Integer userId) {
        String querySQL = "select * from sl_message_info where user_id="+userId+" order by create_time desc";
        return Db.find(querySQL);
    }
}
