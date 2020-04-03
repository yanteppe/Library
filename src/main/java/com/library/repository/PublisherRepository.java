package com.library.repository;

import com.library.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

   List<Publisher> findByNameContainingIgnoreCaseOrderByName(String publisherName);
}