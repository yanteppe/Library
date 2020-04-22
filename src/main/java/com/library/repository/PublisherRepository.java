package com.library.repository;

import com.library.domain.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

   @RestResource(path = "findByName")
   List<Publisher> findByNameContainingIgnoreCaseOrderByName(String publisherName);

   @RestResource(path = "findByNamePage")
   Page<Publisher> findByNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);
}