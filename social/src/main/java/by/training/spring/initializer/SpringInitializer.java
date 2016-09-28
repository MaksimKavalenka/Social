package by.training.spring.initializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import by.training.spring.configuration.MvcSpringConfiguration;
import by.training.spring.filter.SpringFilter;

public class SpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final int  FILE_SIZE_THRESHOLD = 0;
    private static final long MAX_FILE_SIZE       = 20971520;
    private static final long MAX_REQUEST_SIZE    = 20971520;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {MvcSpringConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        Filter[] singleton = {new SpringFilter()};
        return singleton;
    }

    @Override
    protected void customizeRegistration(final ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(getMultipartConfigElement());
    }

    private MultipartConfigElement getMultipartConfigElement() {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(null,
                MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
        return multipartConfigElement;
    }

}
