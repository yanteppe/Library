package com.library.jsf.controller;

import com.library.domain.Book;
import com.library.jsf.enums.SearchType;
import com.library.jsf.model.LazyDataTable;
import com.library.repository.dao.BookDAO;
import com.library.repository.dao.GenreDAO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.primefaces.context.PrimeRequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@ManagedBean   // Управляемый JSF bean, связующее звено между беком и JSF
@SessionScoped // Время жизни бина сессия пользователя, по окончании сессии бин уничтожится
@Component     // Пометка класса как Spring бина
@Getter
@Setter
@Log
public class BookController implements JSFController<Book> {
   public static final int BOOK_COUNT_ON_PAGE = 20; // Кол-во книг отображаемых по  умолчанию на странице
   /*
     Из таблицы JSF должны быть ссылки на переменные, без них пагинация dataGrid работает некорректно (не отрабатывает bean)
     Также сохраняется выбранное пользователем значение (кол-во записей на странице).
   */
   private int rowsCount = BOOK_COUNT_ON_PAGE;
   public static final int POPULAR_BOOKS_COUNT = 5; // Кол-во отображаемых популярных книг (У кого больше всего голосов)
   private SearchType searchType;                   // Сохраняет последний выбранный вариант поиска книг
   @Autowired
   // Можно вызывать спринг бины в JSF бинах - настройка в faces-com.library.config.xml - <el-resolver>
   private BookDAO bookDAO; // Spring автоматически подставит BookService (Spring контейнер по-умолчанию ищет по типу бин-реализацию)
   @Autowired
   private GenreDAO genreDAO;
   @Autowired
   private GenreController genreController;
   private Book selectedBook;             // Сылка на текущую книгу (для редкатирования, удаления)
   private LazyDataTable<Book> lazyModel; // Класс модель-утилита, постранично выводит данные (работает с компонентами на странице JSF)
   private byte[] uploadedBookCoverImage; // Массива байт для сохранение обложки книги (Добавление/редактирование)
   private byte[] uploadedBookContent; // Массив байт для сохранения PDF контент книги (Добавление/редактирование)
   private Page<Book> bookPages;          // Хранит список найденных книг
   private List<Book> popularBooks;       // Хранит полученные популярные книги
   private String searchText;             // Введенный текст поиска
   private long selectedGenreId;          // Выбранный жанр при поиске книги по жанру

   // Метод вызывается после вызова конструктора бина (после создание бина контйнером)
   @PostConstruct
   public void init() {
      // В LazyDataTable передается текущий (this) контроллер для вызывов его методов
      lazyModel = new LazyDataTable<>(this);
   }

   public void save() {
      // Если было выбрано новое изображение обложки книги
      if (uploadedBookCoverImage != null) {
         selectedBook.setCoverImage(uploadedBookCoverImage);
      }
      // Если был выбран новый PDF контент
      if (uploadedBookContent != null) {
         selectedBook.setContent(uploadedBookContent);
      }
      bookDAO.save(selectedBook);
      PrimeRequestContext.getCurrentInstance().getInitScriptsToExecute().add("PF('dialogEditBook').hide()");
      //PrimeRequestContext.getCurrentInstance().getScriptsToExecute().add("PF('dialogEditBook').hide()");
      //RequestContext.getCurrentInstance().execute("PF('dialogEditBook').hide()");// вызов JS из java кода
   }

   @Override
   public void add() {

   }

   @Override
   public void edit() {
      uploadedBookCoverImage = selectedBook.getCoverImage();
      // При нажатии на редактирование книги выбранный объект Book уже будет записан в переменную selectedBook
      // Книга отобразится в диалоговом окне
      PrimeRequestContext.getCurrentInstance().getInitScriptsToExecute().add("PF('dialogEditBook').show()");
      //PrimeRequestContext.getCurrentInstance().getScriptsToExecute().add("PF('dialogEditBook').show()");
      //RequestContext.getCurrentInstance().execute("PF('dialogEditBook').show()");
   }

   @Override
   public void delete() {
      bookDAO.delete(selectedBook);
   }

   /**
    * Очистить загруженный контент из переменной при закрытии диалогового окна.
    */
   public void onCloseDialog(CloseEvent event) {
      uploadedBookContent = null;
   }

   /**
    * Сбор данных для отображения на главной странице. Список книг .<br>
    * Автоматически вызывается в {@link LazyDataTable#load(int, int, String, SortOrder, Map)}.
    *
    * @param pageNumber    номер страницы в списке книг на главной странице.
    * @param pageCount     кол-во страниц в списке книг.
    * @param sortingField  параметр сортировки.
    * @param sortDirection условие сортировки в списке книг.
    * @return объект Page, страница с информацией о содержащихся на ней данных
    */
   @Override
   public Page<Book> collectData(int pageNumber, int pageCount, String sortingField, Sort.Direction sortDirection) {
      // Если не указан параемтр сортировки то по умолчанию сортируется по name
      if (sortingField == null) sortingField = "name";
      if (searchType == null) {
         bookPages = bookDAO.getAll(pageNumber, pageCount, sortingField, sortDirection);
      } else {
         switch (searchType) {
            case SEARCH_GENRE:
               bookPages = bookDAO.findBookByGenre(pageNumber, pageCount, sortingField, sortDirection, selectedGenreId);
               break;
            case SEARCH_TEXT:
               bookPages = bookDAO.search(pageNumber, pageCount, sortingField, sortDirection, searchText);
               break;
            case ALL:
               bookPages = bookDAO.getAll(pageNumber, pageCount, sortingField, sortDirection);
               break;
            default:
               throw new IllegalStateException("Unexpected value: " + searchType);
         }
      }
      return bookPages;
   }

   /**
    * Получение списка популярных книг (С наибольшим кол-вом голосов).<br>
    * Для отображения на странице в блоке самых популярных книг.
    *
    * @return List, список популярных книг.
    */
   public List<Book> getPopularBooks() {
      popularBooks = bookDAO.findPopularBooks(POPULAR_BOOKS_COUNT);
      return popularBooks;
   }

   /**
    * Поиск и отоборажение всех книг
    */
   public void searchAll() {
      searchType = SearchType.ALL;
   }

   /**
    * Поиск книг по жанру для отображания
    *
    * @param genreId жанр по которому производится поиск
    */
   public void searchBooksByGenre(long genreId) {
      searchType = SearchType.SEARCH_GENRE;
      this.selectedGenreId = genreId;
   }

   public void searchAction() {
      searchType = SearchType.SEARCH_TEXT;
   }

   /**
    * Получение сообщения о результатах поиска (Сколько и каких книг найдено).<br>
    * Выводится в строке над результатми поиска (Над списком книг).<br>
    * src/main/webapp/pages/books.xhtml
    *
    * @return String, сообщение о результатах поиска.
    */
   public String getSearchMessage() {
      // Для доступа к файлам локализации
      var bundle = ResourceBundle.getBundle("library", FacesContext.getCurrentInstance().getViewRoot().getLocale());
      String message = null;
      if (searchType == null) return null;
      switch (searchType) {
         case SEARCH_GENRE:
            message = bundle.getString("genre") + ": '" + genreDAO.get(selectedGenreId) + "'";
            break;
         case SEARCH_TEXT:
            if (searchText == null || searchText.trim().length() == 0) {
               return null;
            }
            message = bundle.getString("search") + ": '" + searchText + "'";
            break;
      }
      return message;
   }

   /**
    * Получить PDF контент книги для чтения
    */
   public byte[] getPDFContent(long bookId) {
      byte[] content;
      if (uploadedBookContent != null) {
         content = uploadedBookContent;
      } else {
         content = bookDAO.getBookContent(bookId);
      }
      return content;
   }

   /**
    * Загрузка изображения обложки книги. Сохраняется в переменную uploadedBookCoverImage.
    */
   public void uploadBookCoverImage(FileUploadEvent event) {
      if (event.getFile() != null) {
         uploadedBookCoverImage = event.getFile().getContent();
      }
   }

   /**
    * Загрузка контента книги. Сохраняется в переменную uploadedBookPDFContent.
    */
   public void uploadBookPDFContent(FileUploadEvent event) {
      if (event.getFile() != null) {
         uploadedBookContent = event.getFile().getContent();
      }
   }

   public Page<Book> getBookPages() {
      return bookPages;
   }

   /**
    * Обновить счетчик просмотров книги
    *
    * @param viewCount счетчик просмотров книги
    * @param bookId id просматриваемой книги
    */
   public void updateViewCount(long viewCount, long bookId) {
      bookDAO.updateBookViewCount(viewCount + 1, bookId);
   }
}
