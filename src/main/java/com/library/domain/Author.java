package com.library.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
/*
 name = "author" - если нзвание класса не совпадает с названием таблицы в БД
 catalog = "library" - название схемы в БД
 */
@Table(name = "author", catalog = "library")
/*
 @DynamicUpdate, @DynamicInsert - при изменении данных в таблице будут обнавлятся данные только в этих колонках а не вся таблица
 @SelectBeforeUpdate - для проверки, следует ли обновлять объект, был ли объект изменен и обновление данных необходимо
 */
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Author {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String fio;
   private Date birthday;
   /*
   fetch = FetchType.LAZY - ленивая инициализация, список книг у автора будет вызываться только при непосредственном
   запросе списка книг. @Basic(fetch = FetchType.LAZY) - не используется
   */
   @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
   private List<Book> books;

   @Override
   public String toString() {
      return fio;
   }
}


