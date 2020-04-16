package com.library.repository.service;

import com.library.domain.Book;
import com.library.repository.BookRepository;
import com.library.repository.dao.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 @Service - указывает, что класс является сервисным бином
 @Transactional - для настройки транзакционного поведения метода. Обозначение на уровне класса применяет настройки
 для всех методов класса (Без указания настроек для уровеня изоляции транзакций используются настройки по умолчанию).
*/
@Service
@Transactional()
public class BookService implements BookDAO {
   @Autowired
   private BookRepository bookRepository;

   @Override
   public byte[] getBookContent(long bookId) {
      return bookRepository.getContent(bookId);
   }

   @Override
   public List<Book> findPopularBooks(int quantity) {
      return bookRepository.findPopularBooks(PageRequest.of(0, quantity, Sort.by(Direction.ASC, "viewCount")));
   }

   @Override
   public Page<Book> findBookByGenre(int pageNumber, int pageCount, String sortingField, Direction sortDirection, long genreId) {
      return bookRepository.findByGenre(genreId, PageRequest.of(pageNumber, pageCount, Sort.by(sortDirection, sortingField)));
   }

   @Override
   public Book save(Book book) {
      bookRepository.save(book);       // Сохранение данных о книге, кроме контента
      if (book.getContent() != null) { // Если контент есть то отдельная операция его сохранения
         bookRepository.updateContent(book.getContent(), book.getId());
      }
      return book;
   }

   @Override
   public void delete(Book book) {
      bookRepository.delete(book);
   }

   @Override
   public Book get(long id) {
      return bookRepository.findById(id).orElse(null);
   }

   @Override
   public List<Book> getAll() {
      return bookRepository.findAll();
   }

   @Override
   public List<Book> getAll(Sort sort) {
      return bookRepository.findAll(sort);
   }

   @Override
   public Page<Book> getAll(int pageNumber, int pageCount, String sortingField, Direction sortDirection) {
      return bookRepository.findAllWithoutContent(PageRequest.of(pageNumber, pageCount, Sort.by(sortDirection, sortingField)));
   }

   @Override
   public List<Book> search(String... searchString) {
      return null;
   }

//   @Override
//   public List<Book> search(String... searchString) {
//      return bookRepository.findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(searchString[0], searchString[0],
//            PageRequest.of(1, 1000, Sort.by(Direction.ASC, ""))).getContent();
//   }

   @Override
   public Page<Book> search(int pageNumber, int pageCount, String sortingField, Direction sortDirection, String... searchString) {
      return bookRepository
            .findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(searchString[0], searchString[0],
                  PageRequest.of(pageNumber, pageCount, Sort.by(sortDirection, sortingField)));
   }

   @Override
   public void updateBookRating(long totalRating, long totalVoteCount, int averageRating, long id) {
      bookRepository.updateRating(totalRating, totalVoteCount, averageRating, id);
   }

   @Override
   public void updateBookViewCount(long viewCount, long id) {
      bookRepository.updateViewCount(viewCount, id);
   }
}
