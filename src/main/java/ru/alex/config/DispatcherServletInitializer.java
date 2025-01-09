package ru.alex.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        registerHiddenFieldFilter(servletContext);

        servletContext.addFilter("securityFilter",
                new DelegatingFilterProxy("springSecurityFilterChain"))
                .addMappingForUrlPatterns(null, false, "/*");
    }

    private void registerHiddenFieldFilter(ServletContext servletContext) {
        servletContext.addFilter("httpMethodFilter", new HiddenHttpMethodFilter())
                .addMappingForUrlPatterns(null, false, "/*");
    }
}
