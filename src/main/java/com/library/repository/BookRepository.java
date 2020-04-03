package com.library.repository;

import com.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
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
   List<Book> findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(String bookName, String authorFio);
}