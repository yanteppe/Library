package com.library.jsf.controller;


import com.library.domain.Genre;
import com.library.jsf.model.LazyDataTable;
import com.library.repository.dao.GenreDAO;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.PrimeRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
@Getter @Setter
public class GenreController implements JSFController<Genre> {
   /*
    Из таблицы JSF обязательно должны быть ссылки на переменные, без них пагинация dataGrid работает некорректно (не отрабатывает bean)
    Также сохраняется выбранное пользователем значение (кол-во записей на странице).
  */
   private int rowsCount = 20;
   private int first;
   @Autowired // Автоматически устанавливает значение поля - Spring подставляет компонент этого тпа.
   private GenreDAO genreDAO;
   private Genre selectedGenre;
   private LazyDataTable<Genre> lazyModel;
   private Page<Genre> genrePages;

   @PostConstruct // метод вызывается после вызова конструктора бина (после создание бина контйнером)
   public void init() {
      lazyModel = new LazyDataTable<>(this);
   }

   @Override
   public Page<Genre> collectData(int pageNumber, int pageCount, String sortingField, Sort.Direction sortDirection) {
      return genrePages;
   }

   // TODO: реализовать методы для жанра книги
   @Override
   public void add() {

   }

   @Override
   public void edit() {

   }

   @Override
   public void delete() {

   }

   public void save() {
      genreDAO.save(selectedGenre);
      PrimeRequestContext.getCurrentInstance().getInitScriptsToExecute().add("PF('dialogGenre').hide()");
      //RequestContext.getCurrentInstance().execute("PF('dialogGenre').hide()");
   }

   // Для отображения всех жанров в левом блоке на странице
   public List<Genre> getAll() {
      return genreDAO.getAll(Sort.by(Sort.Direction.ASC, "name"));
   }

   public List<Genre> find(String name) {
      return genreDAO.search(name);
   }
}
