package com.cd.xaboot;

import com.cd.xaboot.entity.OrderInfo;
import com.cd.xaboot.entity.UserInfo;
import com.cd.xaboot.mapper.order.OrderInfoMapper;
import com.cd.xaboot.mapper.user.UserInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XabootApplicationTests {

	@Resource
	private UserInfoMapper userInfoMapper;

	@Resource
	private OrderInfoMapper orderInfoMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void addUserTest () {

		UserInfo userInfo = new UserInfo();
		userInfo.setUserName("zhangsan");
		userInfoMapper.insert(userInfo);
	}

	@Test
	@Transactional(value="transactionManager")
	public void insertData () throws Exception {

		UserInfo userInfo = new UserInfo();
		userInfo.setUserName("xaUser");
		userInfoMapper.insert(userInfo);

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderName("xaOrder");
		orderInfoMapper.insert(orderInfo);
//		throw new SQLException("测试 xa");
	}

}
