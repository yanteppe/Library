package com.library.repository.dao;

import com.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

public interface BookDAO extends DataAccessObject<Book> {

   // Получть контент книги по id
   byte[] getBookContent(long bookId);

   // Найти самые популярные книги
   List<Book> findPopularBooks(int quantity);

   // Поиск книг по жанрам с пагинацией
   Page<Book> findBookByGenre(int pageNumber, int pageCount, String sortingField, Direction sortDirection, long genreId);

   /*
    Обновление данных рейтинга книги
    averageRating = totalRating / totalVoteCount
   */
   void updateBookRating(long totalRating, long totalVoteCount, int averageRating, long id);

   // Обновление статистики просмотра книги
   void updateBookViewCount(long viewCount, long id);
}