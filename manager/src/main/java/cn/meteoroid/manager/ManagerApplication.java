package cn.meteoroid.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Kelvin Song
 */
//@EnableCaching
@SpringBootApplication(scanBasePackages = {"cn.meteoroid.manager", "cn.meteoroid.common"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

}
