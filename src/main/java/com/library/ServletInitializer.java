package com.library;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*
 extends SpringBootServletInitializer - наследование для запуска остальных механизмов Spring Core
 (Над которыми Spring Boot является надстройкой: Spring MVC и тд...) для определения проекта как web-проекта.
 */
public class ServletInitializer extends SpringBootServletInitializer {

   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      //setRegisterErrorPageFilter(false);
      return application.sources(Application.class);
   }
}