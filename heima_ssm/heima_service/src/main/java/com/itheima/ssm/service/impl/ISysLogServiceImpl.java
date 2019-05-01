package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.ISysLogDao;
import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ISysLogServiceImpl implements ISysLogService {

    @Autowired
    private ISysLogDao ISysLogDao;

    @Override
    public void save(SysLog sysLog) throws Exception {
        ISysLogDao.save(sysLog);
    }
}
