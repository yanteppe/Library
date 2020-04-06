package com.library.repository.service;

import com.library.domain.Genre;
import com.library.repository.GenreRepository;
import com.library.repository.dao.GenreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GenreService implements GenreDAO {
   @Autowired
   private GenreRepository genreRepository;

   @Override
   public Genre save(Genre genre) {
      return genreRepository.save(genre);
   }

   @Override
   public void remove(Genre genre) {
      genreRepository.delete(genre);
   }

   @Override
   public Genre get(long id) {
      return genreRepository.findById(id).orElse(null);
   }

   @Override
   public List<Genre> getAll() {
      return genreRepository.findAll();
   }

   @Override
   public List<Genre> getAll(Sort sort) {
      return genreRepository.findAll(sort);
   }

   @Override
   public Page<Genre> getAll(int pageNumber, int pageCount, String sortingField, Sort.Direction sortDirection) {
      return genreRepository.findAll(PageRequest.of(pageNumber, pageCount, Sort.by(sortDirection, sortingField)));
   }

   @Override
   public List<Genre> search(String... searchParameter) {
      return genreRepository.findByNameContainingIgnoreCaseOrderByName(searchParameter[0]);
   }

   @Override
   public Page<Genre> search(
         int pageNumber, int pageCount, String sortingField, Sort.Direction sortDirection, String... searchParameter) {
      return genreRepository.findByNameContainingIgnoreCaseOrderByName(searchParameter[0],
            PageRequest.of(pageNumber,pageCount, Sort.by(sortDirection, sortingField)));
   }
}
