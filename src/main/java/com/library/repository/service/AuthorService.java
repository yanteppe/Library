package com.library.repository.service;

import com.library.domain.Author;
import com.library.repository.AuthorRepository;
import com.library.repository.dao.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService implements AuthorDAO {
   @Autowired
   private AuthorRepository authorRepository;

   @Override
   public Author save(Author author) {
      return authorRepository.save(author);
   }

   @Override
   public void delete(Author author) {
      authorRepository.delete(author);
   }

   @Override
   public Author get(long id) {
      // Optional - тип который может получить null, нужен для избежания NPE
      Optional<Author> author = authorRepository.findById(id);
      // Если есть значение, то вернуть его
      return author.orElse(null);
   }

   @Override
   public List<Author> getAll() {
      return authorRepository.findAll();
   }

   @Override
   public List<Author> getAll(Sort sort) {
      return authorRepository.findAll(sort);
   }

   @Override
   public Page<Author> getAll(int pageNumber, int pageCount, String sortingField, Sort.Direction sortDirection) {
      return authorRepository.findAll(PageRequest.of(pageNumber, pageCount, Sort.by(sortDirection, sortingField)));
   }

   @Override
   public List<Author> search(String... searchParameter) {
      return authorRepository.findByFioContainingIgnoreCaseOrderByFio(searchParameter[0]);
   }

   @Override
   public Page<Author> search(int pageNumber, int pageCount, String sortingField, Sort.Direction sortDirection, String... searchString) {
      return authorRepository.findByFioContainingIgnoreCaseOrderByFio(searchString[0], PageRequest.of(pageNumber, pageCount, Sort.by(sortDirection, sortingField)));
   }
}
