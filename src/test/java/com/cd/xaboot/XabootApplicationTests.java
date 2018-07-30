package com.cd.xaboot;

import com.cd.xaboot.entity.OrderInfo;
import com.cd.xaboot.entity.UserInfo;
import com.cd.xaboot.mapper.order.OrderInfoMapper;
import com.cd.xaboot.mapper.user.UserInfoMapper;
import com.tff.util.SmsSender;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Rollback(false)
public class XabootApplicationTests {

	@Resource
	private UserInfoMapper userInfoMapper;

	@Resource
	private OrderInfoMapper orderInfoMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	@Transactional(value="transactionManager")
	public void addUserTest () {

		UserInfo userInfo = new UserInfo();
		userInfo.setUserName("zhangsan");
		userInfoMapper.insert(userInfo);
	}

	@Test
	@Rollback(false)
	@Transactional(value="transactionManager")
	public void insertData () throws Exception {

		String dateStr = new Date().toLocaleString();
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(dateStr + "xaUser");
		userInfoMapper.insert(userInfo);

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderName(dateStr + "xaOrder");
		orderInfoMapper.insert(orderInfo);

	}

}
