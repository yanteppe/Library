package com.library.repository;

import com.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 JpaRepository - интерфейс Spring Data с типовыми операциями с сущностями: CRUD, постраничная загрузка данных (pagination) и т. д.
 @Repository - Spring bean, помечен как Repository.
 JpaRepository<Book, Long> - типизация, типа объекта (Book), тип поля id (Long)
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
   /*
    Метод поиска книги по автору или названию.
    Hibernate запрос строится на основании сигнатуры метода (Правила наименования методов в Repository)
      - findByNameContainingIgnoreCase - поиск по названию книги. Обращение к полю name в объекте Book.
      - Or или
      - AuthorFioContainingIgnoreCaseOrderByName - по ФИО автора.
        Обращение к полю Author в объекте Book и получение значения поля fio из объекта Author.
    Если сигнатура метода очень длинна использовать @Query с HQL (Hiberante Query Language)
   */
   Page<Book> findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(String bookName, String authorFio, Pageable pageable);

   // Получения контента книги по id
   @Query("select book.content FROM Book book where book.id =:id")
   byte[] getContent(@Param("id") long id);

   /*
    Изменение данных с помощью метода репозитория
    - @Modifying - Если запрос изменяет/обновляет данные.
      Используется для улучшения аннотации @Query, позволяет выполнять запрос: INSERT, UPDATE, DELETE, DDL.
    - clearAutomatically = true - После изменения сущности в persistence context контекс очищается через clearAutomatically.
    - @Param("content") - Чтобы значение параметра попало в HQL запрос. Если доп. параметры в запросе не нужны,
      то @Param("...") не указывать.
    - content=:content - Связывание контента в запросе через ":" с контентом в параметре метода @Param("content").
   */
   @Modifying(clearAutomatically = true)
   @Query("update Book book set book.content=:content where book.id =:id")
   void updateContent(@Param("content") byte[] content, @Param("id") long id);

   /*
    @Query - запрос HQL
    Для книг лидеров голосования получается только изображение и id.
    В классе Book для этого специальный конструктор с двумя параметрами: Long id, byte[] coverImage.
    В сигнатуре метода возвращаемый тип List так как для получения требуется только указание кол-ва получаемых кинг,
    пагинация не требуется.
   */
   @Query("select new com.library.domain.Book(book.id, book.coverImage) from Book book")
   List<Book> findPopularBooks(Pageable pageable);

   // Найти все книги без контента
   @Query("select new com.library.domain.Book(book.id, book.name, book.pageCount, book.isbn, book.genre, book.author," +
         "book.publisher, book.publishingYear, book.coverImage, book.description, book.viewCount, book.totalRating," +
         "book.totalVoteCount, book.averageRating) from Book book")
   Page<Book> findAllWithoutContent(Pageable pageable);

   /*
    Поиск по жанру, в @Query указываются все необходимые данные.
    @Param - передача именованных параметров в запрос. Ссылка на параметр с синтаксисом =:parameter.
   */
   @Query("select new com.library.domain.Book(book.id, book.name, book.pageCount, book.isbn, book.genre, book.author, " +
         "book.publisher, book.publishingYear, book.coverImage, book.description, book.viewCount, book.totalRating," +
         "book.totalVoteCount, book.averageRating) from Book book where book.genre.id=:genreId")
   Page<Book> findByGenre(@Param("genreId") long genreId, Pageable pageable);

   // Обновление рейтинга книги
   @Modifying
   @Query("update Book book set book.totalVoteCount=:totalVoteCount, book.totalRating=:totalRating," +
         "book.averageRating=:averageRating where book.id =:id")
   void updateRating(@Param("totalRating") long totalRating, @Param("totalVoteCount") long totalVoteCount,
                     @Param("averageRating") int averageRating, @Param("id") long id);

   // Обновление статистики просмотра книги
   @Modifying
   @Query("update Book book set book.viewCount=:viewCount where book.id =:id")
   void updateViewCount(@Param("viewCount") long viewCount, @Param("id") long id);
}