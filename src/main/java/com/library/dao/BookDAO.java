package com.library.dao;

import com.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

public interface BookDAO extends DataAccessObject<Book> {

   // Получть контент книги по id
   byte[] getContent(long bookId);

   // Найти самые популярные книги
   List<Book> findPopularBooks(int quantity);

   // Поиск книг по жанрам с пагинацией
   Page<Book> findByGenre(int pageNumber, int pageCount, String sortingField, Direction sortDirection, long genreId);
}