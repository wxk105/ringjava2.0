package com.jumu.ring.controller;

import com.jumu.ring.entity.CompanyName;
import com.jumu.ring.entity.MobileCrbt;
import com.jumu.ring.service.MobileCrbtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
@RequestMapping("crbt")
public class MobileCrbtController {

    @Autowired
    private MobileCrbtService mobileCrbtService;

    @ApiOperation(value="获取彩铃列表", notes="")
    @RequestMapping(value = "findAll",method = RequestMethod.GET)
    @ResponseBody
    private Object findAll() {
        return mobileCrbtService.findAll();
    }

    @ApiIgnore
    @RequestMapping(value = "insert",method = RequestMethod.POST)
    @ResponseBody
    private Object insert(@RequestBody MobileCrbt mobileCrbt) {
        mobileCrbtService.insert(mobileCrbt);
        return "添加成功";
    }

    @ApiOperation(value = "获取彩铃详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "彩铃id", required = true, dataType = "Long")
    })
    @RequestMapping(value = "findDetail/{id}",method = RequestMethod.GET)
    @ResponseBody
    private Object findDetail(@PathVariable  long id) {
        MobileCrbt crbt = mobileCrbtService.findById(id);
        return crbt;
    }

    @ApiOperation(value = "获取彩铃列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyName", value = "彩铃companyName", required = true, dataType = "String")
    })
    @RequestMapping(value = "findRingByCompanyName/{companyName}",method = RequestMethod.GET)
    @ResponseBody
    private Object findRingByCompanyName(@PathVariable  String companyName) {
        MobileCrbt crbt = mobileCrbtService.findByCompanyName(companyName);
        return crbt;
    }


    @ApiOperation(value = "获取品牌列表" ,notes="")
    @RequestMapping(value = "findRingCompanyNames",method = RequestMethod.GET)
    @ResponseBody
    private Object findRingCompanyNames() {
        List<MobileCrbt> list = (List<MobileCrbt>) mobileCrbtService.findAll();
        List<String> companyNameList=new ArrayList<>();
        for (MobileCrbt info:list){
            companyNameList.add(info.getCompanyName());
        }
        return companyNameList;
    }

}
