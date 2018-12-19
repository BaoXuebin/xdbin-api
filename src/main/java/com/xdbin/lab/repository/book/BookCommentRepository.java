package com.xdbin.lab.repository.book;

import com.xdbin.lab.entity.book.BookComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCommentRepository extends JpaRepository<BookComment, Integer> {

}
