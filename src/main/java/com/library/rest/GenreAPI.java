package com.library.rest;

import com.library.domain.Genre;
import com.library.repository.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/genre")
public class GenreAPI {
   @Autowired
   private GenreService genreService;

   @PostMapping(value = "/add")
   public boolean add(@RequestBody Genre genre) {
      genreService.save(genre);
      return true;
   }

   @PutMapping(value = "/update")
   public boolean update(@RequestBody Genre genre) {
      genreService.save(genre);
      return true;
   }

   @DeleteMapping("/delete")
   public boolean delete(@RequestParam("id") long id) {
      genreService.delete(genreService.get(id));
      return true;
   }

   @GetMapping("/get")
   public Genre get(@RequestParam("id") long id) {
      return genreService.get(id);
   }

   @GetMapping("/all")
   public List<Genre> getGenres() {
      return genreService.getAll();
   }

   @GetMapping("/search")
   public List<Genre> search(@RequestParam("name") String name) {
      return genreService.search(name);
   }

   @GetMapping("/pagination")
   public List<Genre> pagination(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageCount") int pageCount) {
      return genreService.getAll(pageNumber, pageCount, "name", Sort.Direction.ASC).getContent();
   }

   @GetMapping("/searchPage")
   public List<Genre> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageCount") int pageCount, @RequestParam("fio") String fio) {
      // Для получения сиска у объекта Page необходимо вызвать getContent
      return genreService.search(pageNumber, pageCount, "name", Sort.Direction.ASC, fio).getContent();
   }
}
