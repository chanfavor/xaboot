package com.cd.xaboot.controller;

import com.cd.xaboot.entity.OrderInfo;
import com.cd.xaboot.entity.UserInfo;
import com.cd.xaboot.mapper.order.OrderInfoMapper;
import com.cd.xaboot.mapper.user.UserInfoMapper;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

/**
 * created by chan on 2018/7/30
 */
@RestController
@RequestMapping("xa")
public class XaController {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;

    @RequestMapping("/add")
    @Transactional(value = "transactionManager")
    public void xaAdd() {

        String dateStr = new Date().toLocaleString();
        UserInfo userInfo = UserInfo.builder().userName(dateStr + "tff").build();
        userInfoMapper.insert(userInfo);

        OrderInfo orderInfo = OrderInfo.builder().orderName(dateStr + "tff_order").build();
        orderInfoMapper.insert(orderInfo);
        throw new RuntimeException("测试事务提交失败，检查数据是否都已回滚");
    }

    @RequestMapping("/add2")
    @Transactional(value = "transactionManager")
    public void xaAdd2() {

        String dateStr = new Date().toLocaleString();
        UserInfo userInfo = UserInfo.builder().userName(dateStr + "tff").build();
        userInfoMapper.insert(userInfo);

        /* 运行时异常，检查数据是否都回滚 **/
        int a = 1/0;

        OrderInfo orderInfo = OrderInfo.builder().orderName(dateStr + "tff_order").build();
        orderInfoMapper.insert(orderInfo);
    }

    @RequestMapping("/add3")
    @Transactional(value = "transactionManager")
    public void xaAdd3() throws SQLException {

        String dateStr = new Date().toLocaleString();
        UserInfo userInfo = UserInfo.builder().userName(dateStr + "tff").build();
        userInfoMapper.insert(userInfo);

        OrderInfo orderInfo = OrderInfo.builder().orderName(dateStr + "tff_order").build();
        orderInfoMapper.insert(orderInfo);

        throw new SQLException("抛出检查时异常，数据应该没有回滚");
    }

    @RequestMapping("/add4")
    @Transactional(rollbackFor = Exception.class)
    public void xaAdd4() throws SQLException {

        String dateStr = new Date().toLocaleString();
        UserInfo userInfo = UserInfo.builder().userName(dateStr + "tff").build();
        userInfoMapper.insert(userInfo);

        OrderInfo orderInfo = OrderInfo.builder().orderName(dateStr + "tff_order").build();
        orderInfoMapper.insert(orderInfo);

        throw new SQLException("抛出检查时异常，设置回滚策略为Exception, 数据应该回滚");
    }

}
