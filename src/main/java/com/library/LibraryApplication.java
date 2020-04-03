package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// scanBasePackages = {"com.library"} - указание в с какого пакета искать бины.
@SpringBootApplication(scanBasePackages = {"com.library"})
public class LibraryApplication {

   public static void main(String[] args) {
      SpringApplication.run(LibraryApplication.class, args);
   }
}
