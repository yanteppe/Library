package com.library.jsf.controller;

import com.library.domain.Publisher;
import com.library.jsf.model.LazyDataTable;
import com.library.repository.dao.PublisherDAO;
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
@Component @Getter @Setter
public class PublisherController implements JSFController<Publisher> {
   private int rowsCount = 20;
   private int first;
   private Page<Publisher> publisherPages;
   @Autowired
   private PublisherDAO publisherDAO;
   private Publisher selectedPublisher;
   private LazyDataTable<Publisher> lazyModel;

   @PostConstruct
   public void init() {
      lazyModel = new LazyDataTable(this);
   }

   public void save() {
      publisherDAO.save(selectedPublisher);
      PrimeRequestContext.getCurrentInstance().getInitScriptsToExecute().add("PF('dialogPublisher').hide()");
      //RequestContext.getCurrentInstance().execute("PF('dialogPublisher').hide()");
   }

   @Override
   public void add() {
      selectedPublisher = new Publisher();
      showEditModal();
   }

   @Override
   public void edit() {
      showEditModal();
   }

   @Override
   public void delete() {
      publisherDAO.delete(selectedPublisher);
   }

   @Override
   public Page<Publisher> collectData(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
      return publisherPages;
   }

   public List<Publisher> find(String name) {
      return publisherDAO.search(name);
   }

   private void showEditModal() {
      PrimeRequestContext.getCurrentInstance().getInitScriptsToExecute().add("PF('dialogPublisher').show()");
      //RequestContext.getCurrentInstance().execute("PF('dialogPublisher').show()");
   }
}
