package com.xdbin.lab.repository.book;

import com.xdbin.lab.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
