package org.aliece.docker;

import org.aliece.docker.config.CoreApplication;
import org.aliece.docker.domain.User;
import org.aliece.docker.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoreApplication.class)
@WebAppConfiguration
public class ApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testInsert() {
		User user = new User();
		user.setUserID(2);
		user.setEmail("aaa@123.com");
		user.setUserName("张三");

		//TODO 这里测试的时候，事务是不会进行回滚操作的。这里需要对框架重新定义
		userService.insert(user);
	}

	@Test
	public void testPage() {
		Page<User> page = userService.findUserByPage(0, 10);
		Assert.assertEquals(page.getTotalElements(), 0L);
	}

}
