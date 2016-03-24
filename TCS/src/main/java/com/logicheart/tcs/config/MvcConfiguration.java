package com.logicheart.tcs.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ssis.angular.Angular.interceptors.BasicInterceptor;

@Configuration
@ComponentScan(basePackages="com.ssis.angular.Angular")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{

	
	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        return new CommonsMultipartResolver();
    }
	
	/*@Bean(name = "springSecurityInitializer")
    public SpringSecurityInitializer getSpringSecurityInitializer() {
        return new SpringSecurityInitializer();
    }*/
 
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:messages");
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }
	
    
    @Bean
    public DataSource dataSource(){
    	
    	BasicDataSource dataSource=new BasicDataSource();
    	
    	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    	dataSource.setUrl("jdbc:mysql://localhost:3306/dbtest");
    	dataSource.setUsername("root");
    	dataSource.setPassword("root");
    	
    	return dataSource;
    	
    }
    
    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory() throws Exception {
    	LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(new String[] { "com.ssis.angular.Angular.model" });

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.show_sql", true);
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        sessionFactoryBean.afterPropertiesSet();
        
        return sessionFactoryBean.getObject();
    }
    
}
