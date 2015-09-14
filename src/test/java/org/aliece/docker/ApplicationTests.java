package org.aliece.docker;

import org.aliece.docker.config.CoreApplication;
import org.aliece.docker.domain.Product;
import org.aliece.docker.domain.ProductStore;
import org.aliece.docker.domain.User;
import org.aliece.docker.service.OrderService;
import org.aliece.docker.service.ProductService;
import org.aliece.docker.service.ProductStoreService;
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

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductStoreService productStoreService;

	@Autowired
	private OrderService orderService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testInsert() {
		User user = new User();
		user.setUserID(3);
		user.setEmail("aaa@123.com");
		user.setUserName("李四");

		Product product = new Product();
		product.setProductDesc("description");
		product.setProductID(1);
		product.setProductName("product");

		ProductStore productStore = new ProductStore();
		productStore.setProductID(1);
		productStore.setProductStoreID(1);
		productStore.setStore(100);
		//TODO 这里测试的时候，事务是不会进行回滚操作的。这里需要对框架重新定义

		userService.insert(user);
		productService.insert(product);
		productStoreService.insert(productStore);
		System.out.println("--------------");
	}

	@Test
	public void testPage() {
		List<User> page = userService.findUserByPage(0, 10);
		Assert.assertEquals(page.size(), 2);
	}

}
