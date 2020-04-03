package com.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(catalog = "library")
@DynamicUpdate @DynamicInsert @SelectBeforeUpdate
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Genre {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(nullable = false) // Значение не может быть null (в БД пустым)
   private Long id;
   private String name;
   // @Basic(fetch = FetchType.LAZY)
   @OneToMany(mappedBy = "genre",fetch = FetchType.LAZY)
   private List<Book> books;

   @Override
   public String toString() {
      return name;
   }
}
