package cn.meteoroid.manager;

import cn.meteoroid.common.extend.jpa.BaseRepositoryFactoryBean;
import cn.meteoroid.common.extend.jpa.BaseRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Kelvin Song
 */
@Slf4j
@EnableJpaAuditing
@EntityScan({"cn.meteoroid.common.entity"})
@EnableJpaRepositories(basePackages = {"cn.meteoroid.common.repository"}, repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@SpringBootApplication(scanBasePackages = {"cn.meteoroid.common", "cn.meteoroid.manager"})
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }

}
