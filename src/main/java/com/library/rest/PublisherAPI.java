package com.library.rest;

import com.library.domain.Publisher;
import com.library.repository.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/publisher")
public class PublisherAPI {
   @Autowired
   private PublisherService publisherService;

   @PostMapping(value = "/add")
   public boolean add(@RequestBody Publisher publisher) {
      publisherService.save(publisher);
      return true;
   }

   @GetMapping("/delete")
   public boolean delete(@RequestParam("id") long id) {
      publisherService.delete(publisherService.get(id));
      return true;
   }

   @GetMapping("/get")
   public Publisher get(@RequestParam("id") long id) {
      return publisherService.get(id);
   }

   @GetMapping("/all")
   public List<Publisher> getPublishers() {
      return publisherService.getAll();
   }

   @GetMapping("/search")
   public List<Publisher> search(@RequestParam("name") String name) {
      return publisherService.search(name);
   }

//   @GetMapping("/allPage")
//   public List<Publisher> allPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageCount") int pageCount) {
//      return publisherService.getAll(pageNumber, pageCount, "fio", Sort.Direction.ASC).getContent();
//   }
//
//   @GetMapping("/searchPage")
//   public List<Publisher> searchPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageCount") int pageCount,
//                                     @RequestParam("fio") String fio) {
//      // getContent() - чтобы получить коллекцию у объекта Page
//      return publisherService.search(pageNumber, pageCount, "fio", Sort.Direction.ASC, fio).getContent();
//   }
}
