package com.library.controller;

import com.library.domain.Author;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import com.library.repository.service.AuthorService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Log // Аннотация Lombok - внедрение логгера в байт код при компиляции (удобный доступ к логеру - log.info(...))
@Controller
public class RedirectController {
   @Autowired // Внедрение из IoC контейнера имплементации объекта AuthorRepository
   private AuthorRepository authorRepository;
   @Autowired
   private BookRepository bookRepository;
   @Autowired
   private AuthorService authorService;

   // Переход по этому запросу при запуске проекта
   @GetMapping(value = "")
   public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
      Page<Author> authors = authorService.getAll(0, 10, "fio", Sort.Direction.ASC);
      var author = new Author();
      author.setFio("Тест Автор");
      author.setBirthday(new Date(1454284800000L));
      var testAuthor = authorService.save(author);
      log.info("\nAUTHORS >>> " + authors.getContent().toString() + "\n");
      log.info("\nAUTHOR >>> " + testAuthor.toString() + "\n");
      return "---";
   }

//   @GetMapping(value = "")
//   public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
//      var bookList = bookRepository.findByGenre(17, PageRequest.of(0, 10, Sort.by(Direction.ASC, "name")));
//      log.info("\nBOOK LIST >>> " + bookList.getContent() + "\n");
//      return "---";
//   }

//   @GetMapping(value = "")
//   public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
//      var authorList = authorRepository.findAll();
//      log.info("\nAUTHORS >>> " + authorList.toString() + "\n");
//      return "Authors list is ok";
//   }

//   @GetMapping(value = "")
//   public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
//      // Поиск книги по вхождению символов в название книги и ФИО автора.
//      var booksList = bookRepository.findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName("г", "наб");
//      log.info("\nBOOKS >>> " + booksList.toString() + "\n");
//      return "Search is ok!";
//   }
}