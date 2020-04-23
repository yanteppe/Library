package com.library.jsf.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Декларирует поведение для всех JSF контроллеров
 *
 * @param <T>
 */
public interface JSFController<T> extends Serializable {

   // Постраничное отображение книг
   Page<T> collectData(int first, int count, String sortingField, Sort.Direction sortDirection);

   void add();

   void edit();

   void delete();
}
