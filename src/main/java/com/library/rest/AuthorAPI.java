package com.library.rest;

import com.library.domain.Author;
import com.library.repository.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Аннотация для rest-контроллеров, методы автоматически возвращают JSON
@RequestMapping(value = "/api/author") // корневой путь
public class AuthorAPI {
   @Autowired
   private AuthorService authorService;

   /**
    * Добавить нового автора.
    *
    * @param author новый автор.
    * @return boolean, true при успешном создании автора.
    */
   @PostMapping("/add")
   public boolean add(@RequestBody Author author) {
      authorService.save(author);
      return true;
   }

   /**
    * Уалить автора по id.
    *
    * @param id идентификатора автора
    * @return boolean, true - успешное удаление
    */
   @GetMapping("/delete")
   public boolean delete(@RequestParam("id") long id) {
      authorService.delete(authorService.get(id));
      return true;
   }

   /**
    * Получить автора по id.
    *
    * @param id идентификатора автора
    * @return Объект Author найденный по id
    */
   @GetMapping("/get")
   public Author get(@RequestParam("id") long id) {
      return authorService.get(id);
   }

   /**
    * Получить всех авторов без сортировки.
    *
    * @return List авторов
    */
   @GetMapping("/all")
   public List<Author> getAuthors() {
      return authorService.getAll();
   }

   /**
    * Поиск авторов по ФИО<br>
    * По части имени, фамилии или отчества<br>
    * Например Лермонтов: api/author/search?fio=Лер<br>
    * Если параметр имеет вхождение у нескольких авторов, то вернется Json со всеми авторами подходящими под условия поиска.
    *
    * @param fio Фамилия Имя или отчество автора
    * @return List найденых авторов
    */
   @GetMapping("/search")
   public List<Author> search(@RequestParam("fio") String fio) {
      return authorService.search(fio);
   }

   /**
    * Получение авторов со страницы веб-приложения.
    * С указанием номера страницы и кол-ва отображаемых книг на странице.
    *
    * @param pageNumber номер страницы на витрине книг в веб-прилжении
    * @param pageCount  кол-во отображаемых книг на странице
    * @return List авторов на указанной странице
    */
   @GetMapping("/allPage") // @RequestParam - получение параметра
   public List<Author> allPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageCount") int pageCount) {
      return authorService.getAll(pageNumber, pageCount, "fio", Sort.Direction.ASC).getContent();
   }

//   /**
//    * Поиск автора по ФИО на странице веб-приложения с указанием номера страницы и кол-ва отображаемых книг на странице.
//    *
//    * @param pageNumber номер страницы на веб-странице
//    * @param pageCount  кол-во отображаемых книг на странице
//    * @param fio        Фамилия Имя или отчество автора
//    * @return List найденых авторов
//    */
//   @GetMapping("/searchPage")
//   public List<Author> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageCount") int pageCount, @RequestParam("fio") String fio) {
//      return authorService.search(pageNumber, pageCount, "fio", Sort.Direction.ASC, fio).getContent();
//   }
}
