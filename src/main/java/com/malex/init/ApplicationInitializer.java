package com.malex.init;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {
    /**
     * Path to config classes
     */
    private static final String CONFIG_LOCATION = "com.malex.config";

    /**
     * Servlet config
     */
    private static final String MAPPING_URL = "/";
    private static final String SERVLET_NAME = "DispatcherServlet";

    /**
     * Multipart config
     */
    private static final String LOCATION = "";
    private static final long MAX_FILE_SIZE = 2000000;
    private static final long MAX_REQUEST_SIZE = 10000000;
    private static final int FILE_SIZE_THRESHOLD = 0;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // Create the 'root' Spring application context
        WebApplicationContext context = getContext();

        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(context));

        // Create the dispatcher servlet's Spring application context
        ServletRegistration.Dynamic dispatcher = servletContext
                .addServlet(SERVLET_NAME, new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.setAsyncSupported(true);
        dispatcher.setMultipartConfig(getMultiPartConfigElement());
        dispatcher.addMapping(MAPPING_URL);
    }

    /**
     * Create the 'root' Spring application context and add config class
     */
    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        // path to config classes
        context.setConfigLocation(CONFIG_LOCATION);
        return context;
    }

    /**
     * Config multipart data
     */
    private MultipartConfigElement getMultiPartConfigElement() {
        return new MultipartConfigElement(LOCATION,
                MAX_FILE_SIZE,
                MAX_REQUEST_SIZE,
                FILE_SIZE_THRESHOLD);
    }
}
