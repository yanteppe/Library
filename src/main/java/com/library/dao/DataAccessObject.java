package com.library.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

public interface DataAccessObject<T> {
   // Добовление или обновление (save / update)
   T save(T object);

   // Удаление объекта
   void remove(T object);

   // Получение объекта по id
   T get(long id);

   // Получить все объекты
   List<T> getAll();

   // Получение всех элементов с сортировкой
   List<T> getAll(Sort sort);

   // Получение всех записей с пагинацией
   Page<T> getAll(int pageNumber, int pageCount, String sortingField, Direction sortDirection);

   // Поиск объекта по параметру
   List<T> search(String... searchParameter);

   // Поиск записей с пагинацией
   Page<T> search(int pageNumber, int pageCount, String sortingField, Direction sortDirection, String... searchString);

}