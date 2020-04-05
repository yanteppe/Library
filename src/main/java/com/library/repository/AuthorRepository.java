package com.library.repository;

import com.library.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

   List<Author> findByFioContainingIgnoreCaseOrderByFio(String authorFio);

   /*
    Page - интерфейс для порционного получения данных (пагинация/постраничный вывод), содержитрезультаты выполнения запроса
    и служебные данные для постраничности
    Pageable - интерфейс для установки параметров пагинации. В метод необходимо передать объект PageRequest с параметрами пагинации.
   */
   Page<Author> findByFioContainingIgnoreCaseOrderByFio(String authorFio, Pageable pageable);
}