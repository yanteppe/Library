package com.library.rest;

import com.library.domain.Book;
import com.library.repository.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/book")
public class BookAPI {
   @Autowired
   private BookService bookService;

   @PostMapping("/add")
   public boolean add(@RequestBody Book book) {
      bookService.save(book);
      return true;
   }

   @PutMapping("/update")
   public boolean update(@RequestBody Book book) {
      bookService.save(book);
      return true;
   }

   @DeleteMapping("/delete")
   public boolean delete(@RequestParam("id") long id) {
      bookService.delete(bookService.get(id));// сначала получаем автора по id, потом его удаляем
      return true;
   }

   @GetMapping("/get")
   public Book get(@RequestParam("id") long id) {
      return bookService.get(id);
   }

   @GetMapping("/all")
   public List<Book> getBooks() {
      return bookService.getAll();
   }

   @GetMapping("/search")
   public List<Book> search(@RequestParam("name") String name) {
      return bookService.search(name);
   }

   @GetMapping("/pagination")
   public List<Book> pagination(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageCount") int pageCount) {
      return bookService.getAll(pageNumber, pageCount, "name", Sort.Direction.ASC).getContent();
   }

//   @GetMapping("/searchPage")
//   public List<Book> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageCount") int pageCount, @RequestParam("fio") String fio){
//      return bookService.search(pageNumber, pageCount, "fio", Sort.Direction.ASC, fio).getContent(); // т.к. возвращается объект Page, надо у него вызвать getContent, чтобы получить коллекцию
//   }

   /**
    * Добавить изображение обложки к книге по id
    *
    * @param coverImage byte[], массив байт - изображение обложки книги
    * @param bookId     id книги к которой загружается изображение
    * @return boolean, true если загрузка контента прошла успешно
    */
   @PostMapping(value = "/addCoverImage")
   public boolean addCoverImage(@RequestBody byte[] coverImage, @RequestParam("bookId") long bookId) {
      Book book = bookService.get(bookId); // Получение книги по id
      book.setCoverImage(coverImage);      // Обновление контента у книги
      bookService.save(book);              // Сохранение обновленной книги в БД
      return true;
   }

   /**
    * Добавить PDF контент к книге по id
    *
    * @param content byte[], массив байт контента книги
    * @param bookId  id книги к которой загружается контент (PDF)
    * @return boolean, true если загрузка контента прошла успешно
    */
   @PostMapping(value = "/addContent")
   public boolean addContent(@RequestBody byte[] content, @RequestParam("bookId") long bookId) {
      Book book = bookService.get(bookId); // Получение книги по id
      book.setContent(content);            // Обновление контента у книги
      bookService.save(book);              // Сохранение обновленной книги в БД
      return true;
   }

   @GetMapping(value = "/searchByGenre")
   public List<Book> getByGenre(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageCount") int pageCount, @RequestParam("genreId") long genreId) {
      return bookService.findBookByGenre(pageNumber, pageCount, "name", Sort.Direction.ASC, genreId).getContent();
   }

   /**
    * Получение PDF контента книги
    *
    * @param bookId id книги
    * @return byte[], массив байт контента книги
    */
   @GetMapping(value = "/getContent")
   public byte[] getContent(@RequestParam("bookId") long bookId) {
      return bookService.getBookContent(bookId);
   }
}
