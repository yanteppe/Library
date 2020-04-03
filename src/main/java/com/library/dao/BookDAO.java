package com.library.dao;

import com.library.domain.Book;

import java.util.List;

public interface BookDAO extends DataAccessObject<Book> {

   List<Book> findVotingLeaderBooks(int limit);
}