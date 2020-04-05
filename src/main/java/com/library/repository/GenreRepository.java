package com.library.repository;

import com.library.domain.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

   List<Genre> findByNameContainingIgnoreCaseOrderByName(String genreName);

   Page<Genre> findByNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);
}