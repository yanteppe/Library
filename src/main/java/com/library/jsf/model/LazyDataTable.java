package com.library.jsf.model;

import com.library.jsf.controller.JSFController;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * Класс-модель для постраничного вывода списка книг - таблица на главной странице.<br>
 * (Так же использутся при выводе результатов поиска по разным критеримя: жанр, автор, назание и т.д. ...)
 * @param <T> тип указываемый при поиске, например поиск по жанру: LazyDataTable<'Genre'>
 */
@Getter @Setter
public class LazyDataTable<T> extends LazyDataModel<T> {
   private List<T> list;
   private JSFController<T> jsfController;

   public LazyDataTable(JSFController<T> jsfController) {
      this.jsfController = jsfController;
   }

   /**
    * Возвращает список книг.<br>
    * Результат выполнения метода передается на страницу books.xhtml в component.<br>
    * src/main/webapp/pages/books.xhtml
    *
    * @param first первая страница
    * @param pageCount кол-во страниц
    * @param sortingField параметр сортировки
    * @param sortOrder тип сортировки (ASC, DESC)
    * @param filters
    * @return content List<'T'>
    */
   @Override
   public List<T> load(int first, int pageCount, String sortingField, SortOrder sortOrder, Map<String, Object> filters) {
      int pageNumber = first / pageCount;     // Определение под каким номером страницу нужно отобразить
      var sortDirection = Sort.Direction.ASC; // По умолчанию сортировка по возрастанию
      if (sortOrder != null) {
         // Все текущие настройки DataTable (сортировка, поле сортировки) будут передаваться в SQL запрос
         switch (sortOrder) {
            case DESCENDING:
               sortDirection = Sort.Direction.DESC;
               break;
         }
      }
      Page<T> collectingDataResult = jsfController.search(pageNumber, pageCount, sortingField, sortDirection);
      /*
       Передача в родительский класс LazyDataModel<T> кол-во найденых строк и элементов.
       Обязательная операция для верной работы LazyDataModel<T>.
      */
      this.setRowCount((int) collectingDataResult.getTotalElements());
      return collectingDataResult.getContent();
   }
}