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
   public boolean add(@RequestBody Genre genre){
      genreService.save(genre);
      return true;
   }

   @GetMapping("/delete")
   public boolean delete(@RequestParam("id") long id){
      genreService.delete(genreService.get(id));
      return true;
   }

   @GetMapping("/get")
   public Genre get(@RequestParam("id") long id){
      return genreService.get(id);
   }

   @GetMapping("/all")
   public List<Genre> getGenres(){
      return genreService.getAll();
   }

   @GetMapping("/search")
   public List<Genre> search(@RequestParam("name") String name){
      return genreService.search(name);
   }

   @GetMapping("/allPage")
   public List<Genre> allPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize){
      return genreService.getAll(pageNumber, pageSize, "fio", Sort.Direction.ASC).getContent();
   }

   @GetMapping("/searchPage")
   public List<Genre> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("fio") String fio){
      return genreService.search(pageNumber, pageSize, "fio", Sort.Direction.ASC, fio).getContent(); // т.к. возвращается объект Page, надо у него вызвать getContent, чтобы получить коллекцию
   }
}
