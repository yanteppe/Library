package com.library.repository.service;

import com.library.domain.Publisher;
import com.library.repository.PublisherRepository;
import com.library.repository.dao.PublisherDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PublisherService implements PublisherDAO {
   @Autowired
   PublisherRepository publisherRepository;

   @Override
   public Publisher save(Publisher publisher) {
      return null;
   }

   @Override
   public void remove(Publisher publisher) {
      publisherRepository.delete(publisher);
   }

   @Override
   public Publisher get(long id) {
      return publisherRepository.findById(id).orElse(null);
   }

   @Override
   public List<Publisher> getAll() {
      return publisherRepository.findAll();
   }

   @Override
   public List<Publisher> getAll(Sort sort) {
      return publisherRepository.findAll(sort);
   }

   @Override
   public Page<Publisher> getAll(int pageNumber, int pageCount, String sortingField, Sort.Direction sortDirection) {
      return publisherRepository.findAll(PageRequest.of(pageNumber, pageCount, Sort.by(sortDirection, sortingField)));
   }

   @Override
   public List<Publisher> search(String... searchParameter) {
      return publisherRepository.findByNameContainingIgnoreCaseOrderByName(searchParameter[0]);
   }

   @Override
   public Page<Publisher> search(
         int pageNumber, int pageCount, String sortingField, Sort.Direction sortDirection, String... searchingParameter) {
      return publisherRepository.findByNameContainingIgnoreCaseOrderByName(searchingParameter[0],
            PageRequest.of(pageNumber, pageCount, Sort.by(sortDirection, sortingField)));
   }
}
