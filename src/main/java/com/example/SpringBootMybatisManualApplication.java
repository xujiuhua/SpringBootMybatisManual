package com.example;

import com.example.plugin.mybatis.plugin.PagePlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.Properties;

@SpringBootApplication
@MapperScan("com.example.dao")
public class SpringBootMybatisManualApplication {

	private static Logger logger = Logger.getLogger(SpringBootMybatisManualApplication.class);

	@Autowired
	private org.apache.tomcat.jdbc.pool.DataSource dataSource;

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		// 设置数据源
		sqlSessionFactoryBean.setDataSource(dataSource);

		// 设置mybatis的xml目录
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));

		// 设置分页插件
		PagePlugin pagePlugin = new PagePlugin();
		Properties properties = new Properties();
		properties.setProperty("dialect", "com.example.plugin.jdbc.dialect.MySQLDialect");
		pagePlugin.setProperties(properties);
		sqlSessionFactoryBean.setPlugins(new Interceptor[]{pagePlugin});

		return sqlSessionFactoryBean.getObject();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisManualApplication.class, args);
	}
}
