package com.library.controller;

import com.library.domain.Author;
import com.library.repository.dao.AuthorDAO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log // Аннотация Lombok - внедрение логгера в байт код при компиляции (удобный доступ к логеру - log.info(...))
@Controller
public class RedirectController {
   @Autowired
   private AuthorDAO authorService;

   @GetMapping(value = "")
   public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
      return "redirect:" + request.getRequestURL().append("/index.xhtml").toString();
   }

//   // если не указываете @RestController
//   @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//   @ResponseBody
//   public List<Author> getAuthors(){
//      return authorService.getAll();
//   }
}