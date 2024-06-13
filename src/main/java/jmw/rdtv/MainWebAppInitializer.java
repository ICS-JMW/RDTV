package jmw.rdtv;

import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

//my child will not copy code from the internet
//child in question:
public class MainWebAppInitializer implements WebApplicationInitializer {

    private static final String TMP_FOLDER = "/tmp";
    private static final int MAX_UPLOAD_SIZE = 8 * 1024 * 1024;

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        ServletRegistration.Dynamic appServlet = sc.addServlet("mvc", new DispatcherServlet(
                new GenericWebApplicationContext()));

        appServlet.setLoadOnStartup(1);

        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER,
                MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2L, MAX_UPLOAD_SIZE / 2);

        appServlet.setMultipartConfig(multipartConfigElement);
    }

}
