package com.library.controller;

import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log // Аннотация Lombok - внедрение логгера в байт код при компиляции (удобный доступ к логеру - log.info(...))
@Controller
public class RedirectController {
   @Autowired // Внедрение из IoC контейнера имплементации объекта AuthorRepository
   private AuthorRepository authorRepository;
   @Autowired
   private BookRepository bookRepository;

   // Переход по этому запросу при запуске проекта
   @RequestMapping(value = "", method = RequestMethod.GET)
   public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
      var bookList = bookRepository.findByGenre(17, PageRequest.of(0, 10, Sort.by(Direction.ASC, "name")));
      log.info("\nBOOK LIST >>> " + bookList.getContent() + "\n");
      return "---";
   }

//   @RequestMapping(value = "", method = RequestMethod.GET)
//   public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
//      var authorList = authorRepository.findAll();
//      log.info("\nAUTHORS >>> " + authorList.toString() + "\n");
//      return "Authors list is ok";
//   }

//   @RequestMapping(value = "", method = RequestMethod.GET)
//   public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
//      // Поиск книги по вхождению символов в название книги и ФИО автора.
//      var booksList = bookRepository.findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName("г", "наб");
//      log.info("\nBOOKS >>> " + booksList.toString() + "\n");
//      return "Search is ok!";
//   }
}