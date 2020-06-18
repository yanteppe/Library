package com.library.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log // Аннотация Lombok - внедрение логгера в байт код при компиляции (удобный доступ к логеру - log.info(...))
@Controller
public class RedirectController {

   @GetMapping(value = "")
   public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
      return "redirect:" + request.getRequestURL().append("index.xhtml").toString();
   }
}