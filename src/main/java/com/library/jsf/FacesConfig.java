package com.library.jsf;

import com.library.jsf.PDFContentServlet;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.faces.webapp.FacesServlet;
import javax.servlet.MultipartConfigElement;

/**
 * Класс настроек primefaces.
 */
@Configuration
public class FacesConfig {

   @Bean
   public ServletContextInitializer servletContextInitializer() {
      return servletContext -> {
         servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
         servletContext.setInitParameter("primefaces.THEME", "bootstrap");
         servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
         servletContext.setInitParameter("primefaces.UPLOADER", "commons");
      };
   }

   @Bean
   public FilterRegistrationBean fileUploadFilterRegistrationBean() {
      var registrationBean = new FilterRegistrationBean();
      registrationBean.addUrlPatterns("*.xhtml", "/javax.faces.resource/*");
      registrationBean.setFilter(new FileUploadFilter());
      return registrationBean;
   }

   @Bean
   public ServletRegistrationBean servletRegistrationBean(MultipartConfigElement multipartConfigElement) {
      FacesServlet facesServlet = new FacesServlet();
      var servletRegistrationBean = new ServletRegistrationBean(facesServlet, "*.xhtml", "/javax.faces.resource/*");
      servletRegistrationBean.setMultipartConfig(multipartConfigElement);
      return servletRegistrationBean;
   }

   // Сервлет для чтения PDF контента
   @Bean
   public ServletRegistrationBean pdfServletRegistration() {
      var pdfServlet = new ServletRegistrationBean(new PDFContentServlet(), "/PdfContent");
      pdfServlet.setName("PdfContentServlet");
      return pdfServlet;
   }
}