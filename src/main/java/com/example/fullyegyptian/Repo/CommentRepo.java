package com.example.fullyegyptian.Repo;

import com.example.fullyegyptian.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {

    @Query("select C from Comment C where C.commentId=?1")
    public Comment findCommentById(int id);
}
