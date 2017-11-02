package com.jumu.ring.service;

import com.jumu.ring.dao.MobileCrbtDao;
import com.jumu.ring.entity.CompanyName;
import com.jumu.ring.entity.MobileCrbt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/22.
 */
@Service
public class MobileCrbtService {

    @Autowired
    private MobileCrbtDao mobileCrbtDao;

    public Object findAll() {
        return mobileCrbtDao.findAll();
    }

    public void insert(MobileCrbt mobileCrbt) {
        mobileCrbtDao.save(mobileCrbt);
    }

    public MobileCrbt findById(long id) {
        return mobileCrbtDao.findOne(id);
    }

    /**
     * 通过品牌名字查找数据库
     * @param companyName
     * @return
     */
    public MobileCrbt findByCompanyName(String companyName){
        return mobileCrbtDao.findByCompanyName(companyName);
    }

}
