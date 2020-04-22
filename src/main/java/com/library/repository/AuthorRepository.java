package com.library.repository;

import com.library.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

// @RepositoryRestResource аннотация Spring Data Rest для формирования узлов HATEOAS
@Repository @RepositoryRestResource
public interface AuthorRepository extends JpaRepository<Author, Long> {

   // path указывается если названия методов совпадают
   @RestResource(path = "findByFio")
   List<Author> findByFioContainingIgnoreCaseOrderByFio(String authorFio);

   /*
    Page - интерфейс для порционного получения данных (пагинация/постраничный вывод), содержитрезультаты выполнения запроса
    и служебные данные для пагинации
    Pageable - интерфейс для установки параметров пагинации. В метод необходимо передать объект PageRequest с параметрами пагинации.
   */
   @RestResource(path = "findByFioPage")
   Page<Author> findByFioContainingIgnoreCaseOrderByFio(String authorFio, Pageable pageable);
}