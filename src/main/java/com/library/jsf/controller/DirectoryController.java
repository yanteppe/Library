package com.library.jsf.controller;

import lombok.Getter;
import lombok.Setter;
//import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.event.TabChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.primefaces.context.RequestContext;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;

/**
 * Контроллер справочников в админке приложения
 */
@ManagedBean
@Component
@RequestScoped
@Getter @Setter
public class DirectoryController {
   @Autowired
   private AuthorController authorController;
   @Autowired
   private GenreController genreController;
   @Autowired
   private PublisherController publisherController;
   private String listId = "tabView:authorForm:authorList"; // Открытая вкладка по умолчанию - редактирование авторов
   private String searchText;                               // Текст поиска
   private String selectedTab = "tabAuthors";               // Выбранная вкладка по умолчанию

   /**
    * Поиск в справочниках
    */
   public void search() {
      //PrimeFaces.current().ajax().update(listId);
      RequestContext context = RequestContext.getCurrentInstance();
      context.update(listId);
   }

   /**
    * Возвращает нужный id для обновления области на странице
    *
    * @param event событие на вкладке.
    */
   public void onTabChange(TabChangeEvent event) {
      searchText = null;
      selectedTab = event.getTab().getId();
      switch (selectedTab) {
         case "tabAuthors":
            listId = "tabView:authorForm:authorList";
            break;
         case "tabGenres":
            listId = "tabView:genreForm:genreList";
            break;
         case "tabPublishers":
            listId = "tabView:publisherForm:publisherList";
            break;
      }
      //PrimeFaces.current().ajax().update("searchForm:searchInput");
      RequestContext.getCurrentInstance().update("searchForm:searchInput");
   }

   // В зависимости какая вкладка отображатеся соответствующее модальное окно редактирования/создания
   public void add() {
      switch (selectedTab) {
         case "tabAuthors":
            authorController.add();
            //PrimeFaces.current().ajax().update("tabView:dialogAuthor");
            RequestContext.getCurrentInstance().update("tabView:dialogAuthor");
            break;
         case "tabGenres":
            genreController.add();
            //PrimeFaces.current().ajax().update("tabView:dialogGenre");
            RequestContext.getCurrentInstance().update("tabView:dialogGenre");
            break;
         case "tabPublishers":
            publisherController.add();
            //PrimeFaces.current().ajax().update("tabView:dialogPublisher");
            RequestContext.getCurrentInstance().update("tabView:dialogPublisher");
            break;
      }
   }
}

