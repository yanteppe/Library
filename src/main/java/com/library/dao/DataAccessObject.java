package com.library.dao;

import java.util.List;

public interface DataAccessObject<T> {

   T save(T object);      // Добовление или обновление (save / update)

   T get(long id);        // Получение объекта по id

   void remove(T object); // Удаление объекта

   List<T> getAll();      // Получить все объекты

   List<T> search(String... searchParameter);
}