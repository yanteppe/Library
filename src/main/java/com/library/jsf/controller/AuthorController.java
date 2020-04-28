package com.library.jsf.controller;

import com.library.domain.Author;
import com.library.jsf.model.LazyDataTable;
import com.library.repository.dao.AuthorDAO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.primefaces.context.RequestContext;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
@Getter @Setter
public class AuthorController implements JSFController<Author> {
   private int rowsCount = 20;
   private int first;
   @Autowired
   private AuthorDAO authorDAO;
   private Author selectedAuthor;
   private LazyDataTable<Author> lazyModel;
   private Page<Author> authorPages;

   @PostConstruct
   public void init() {
      lazyModel = new LazyDataTable<>(this);
   }

   @Override
   public void add() {
      selectedAuthor = new Author();
      showEditAuthorModal();
   }

   @Override
   public void edit() {
      // При нажатии на редактировать выбранный Author уже будет записан в переменную selectedAuthor
      // он отобразится в диалоговом окне
      showEditAuthorModal();
   }

   @Override
   public void delete() {
      // При нажатии на удаление выбранный Author уже будет записан в переменную selectedAuthor
      authorDAO.delete(selectedAuthor);
   }

   public void save() {
      authorDAO.save(selectedAuthor);
      RequestContext.getCurrentInstance().execute("PF('dialogAuthor').hide()");
      //PrimeRequestContext.getCurrentInstance().getScriptsToExecute().add("PF('dialogAuthor').hide()");
   }

   @Override
   public Page<Author> search(int first, int count, String sortingField, Sort.Direction sortDirection) {
      return authorPages;
   }

   /**
    * Показать диалоговое окно с редактированием автора со значениями selectedAuthor.
    */
   private void showEditAuthorModal() {
      RequestContext.getCurrentInstance().execute("PF('dialogAuthor').show()");
      //PrimeRequestContext.getCurrentInstance().getScriptsToExecute().add("PF('dialogAuthor').show()");
   }

   public List<Author> find(String fio) {
      return authorDAO.search(fio);
   }
}
