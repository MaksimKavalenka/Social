package by.training.spring.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import by.training.jpa.service.dao.NotificationServiceDAO;
import by.training.jpa.service.dao.PostServiceDAO;
import by.training.jpa.service.dao.RoleServiceDAO;
import by.training.jpa.service.dao.TopicServiceDAO;
import by.training.jpa.service.dao.UserServiceDAO;
import by.training.jpa.service.implementation.NotificationService;
import by.training.jpa.service.implementation.PostService;
import by.training.jpa.service.implementation.RoleService;
import by.training.jpa.service.implementation.TopicService;
import by.training.jpa.service.implementation.UserService;

@Configuration
@ComponentScan("by.training.spring.component")
@EnableJpaRepositories(basePackages = "by.training.jpa.repository")
@EnableTransactionManagement
public class MvcSpringConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        driverManagerDataSource.setUrl(
                "jdbc:h2:mem:mylivedata;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1");
        return driverManagerDataSource;
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan("by.training.entity");
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public NotificationServiceDAO notificationService() {
        return new NotificationService();
    }

    @Bean
    public PostServiceDAO postService() {
        return new PostService();
    }

    @Bean
    public RoleServiceDAO roleService() {
        return new RoleService();
    }

    @Bean
    public TopicServiceDAO topicService() {
        return new TopicService();
    }

    @Bean
    public UserServiceDAO userService() {
        return new UserService();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations("/static/");
    }

    @Override
    public void configureViewResolvers(final ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/html/");
        viewResolver.setSuffix(".html");
        registry.viewResolver(viewResolver);
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.id.new_generator_mappings", "false");
        return properties;
    }

}
