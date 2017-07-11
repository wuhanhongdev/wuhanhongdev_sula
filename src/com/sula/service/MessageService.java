package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

@AutoInstance
public interface MessageService {
    List<Record> queryAllMessage(Integer userId);
}
