package com.library.jsf.controller;

import com.library.domain.Book;
import com.library.jsf.enums.SearchType;
import com.library.jsf.model.LazyDataTable;
import com.library.repository.dao.BookDAO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.util.List;

@ManagedBean   // Управляемый JSF bean, связующее звено между беком и JSF
@SessionScoped // Время жизни бина сессия пользователя, по концу сессии бин уничтожится
@Component     // Пометка класса как Spring бина
@Getter @Setter @Log
public class BookController implements JSFController<Book> {
   public static final int BOOK_COUNT = 20; // Кол-во книг отображаемых по  умолчанию на странице
   /*
     Из таблицы JSF должны быть ссылки на переменные, без них пагинация dataGrid работает некорректно (не отрабатывает bean)
     Также сохраняется выбранное пользователем значение (кол-во записей на странице).
   */
   private int rowsCount = BOOK_COUNT;
   public static final int POPULAR_BOOKS_COUNT = 5; // Кол-во отображаемых популярных книг (У кого больше всего голосов)
   private SearchType searchType;                   // Сохраняет последний выбранный вариант поиска книг
   @Autowired // Можно вызывать спринг бины в JSF бинах - настройка в faces-config.xml - <el-resolver>
   private BookDAO bookDao;// Spring автоматически подставит BookService (Spring контейнер по-умолчанию ищет по типу бин-реализацию)
   private LazyDataTable<Book> lazyModel; // Класс модель-утилита, постранично выводит данные (работает с компонентами на странице JSF)
   private Page<Book> bookPages;          // Хранит список найденных книг
   private List<Book> popularBooks;       // Хранит полученные популярные книги

   @PostConstruct // метод вызывается после вызова конструктора бина (после создание бина контйнером)
   public void init() {
      // В LazyDataTable передается текущий (this) контроллер для вызывов его методов
      lazyModel = new LazyDataTable<>(this);
   }

   // Автоматически вызывается из LazyDataTable
   @Override
   public Page<Book> collectData(int pageNumber, int pageCount, String sortingField, Sort.Direction sortDirection) {
      // Если не указан параемтр сортировки то по умолчанию сортируется поname bkjh jihjkhkjhuyjhyg jnkhn 6
      if (sortingField == null) sortingField = "name";
      if (searchType == null) {
         bookPages = bookDao.getAll(pageNumber, pageCount, sortingField, sortDirection);
      } else {
         switch (searchType) {
            case SEARCH_GENRE:
            case SEARCH_TEXT:
               break;
            case ALL:
               bookPages = bookDao.getAll(pageNumber, pageCount, sortingField, sortDirection);
               break;
            default:
               throw new IllegalStateException("Unexpected value: " + searchType);
         }
      }
      return bookPages;
   }

   public List<Book> getPopularBooks() {
      popularBooks = bookDao.findPopularBooks(POPULAR_BOOKS_COUNT);
      return popularBooks;
   }
}
