package com.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(name = "book", catalog = "library")
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   /*
   @Lob - указание на большие данные
   Тип byte[] для двоичных данных, content - pdf или другой формат, сам текст книги
    */
   @Lob
   @Column(updatable = false)
   private byte[] content;
   @Column(name = "page_count")
   private Integer pageCount;
   private String isbn;
   @ManyToOne
   @JoinColumn
   private Genre genre;
   /*
    @ManyToOne - тип связи - множество к одному, один автор может иметь множество книг
    @JoinColumn - для реализоватции двухсторонней связи как в БД. Получение объкта Author.
    Книга содержит автора, автор может содержать множестов книг (@ManyToOne).
    */
   @ManyToOne
   @JoinColumn
   private Author author;
   @ManyToOne
   @JoinColumn
   private Publisher publisher;
   @Column(name = "publisher_year")
   private Integer publisherYear;
   @Column(name = "cover_image")
   private byte[] coverImage;
   private String description;
   @Column(name = "view_count")
   private long viewCount;
   @Column(name = "total_rating")
   private long totalRating;
   @Column(name = "total_vote_count")
   private long totalVoteCount;
   @Column(name = "average_rating")
   private int averageRating;

   public Book() {
   }

   public Book(Long id, byte[] coverImage) {
      this.id = id;
      this.coverImage = coverImage;
   }

   /*
    В конструкторе все поля кроме content чтобы при запросе списка книг (select) не загружались большие двоичные данные.
    Content получется по требованю - нажать на просмотр книги.
   */
   public Book(Long id, String name, Integer pageCount, String isbn, Genre genre, Author author, Publisher publisher,
               Integer publisherYear, byte[] coverImage, String description, long viewCount, long totalRating, long totalVoteCount, int averageRating) {
      this.id = id;
      this.name = name;
      this.pageCount = pageCount;
      this.isbn = isbn;
      this.genre = genre;
      this.author = author;
      this.publisher = publisher;
      this.publisherYear = publisherYear;
      this.coverImage = coverImage;
      this.description = description;
      this.viewCount = viewCount;
      this.totalRating = totalRating;
      this.totalVoteCount = totalVoteCount;
      this.averageRating = averageRating;
   }

   @Override
   public String toString() {
      return name;
   }
}