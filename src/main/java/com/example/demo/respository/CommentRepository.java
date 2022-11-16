package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    @Query(value = "select c.* from comments c join products p on c.product_id = p.id where p.name = :nameVal", nativeQuery = true)
    List<Comment> getCommentByProductName(@Param("nameVal") String name);
}
