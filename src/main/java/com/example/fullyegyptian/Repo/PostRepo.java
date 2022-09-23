package com.example.fullyegyptian.Repo;

import com.example.fullyegyptian.Model.Likes;
import com.example.fullyegyptian.Model.Post;
import com.example.fullyegyptian.enums.Government;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {

    @Query("select P from Post P where P.id=?1")
    public Post findPostById(int id);

    @Query("select P from Post P where P.government=?1")
    public List<Post> findGovById(Government gov);

    @Query("select P.like from Post P where P.id=?1")
    public List<Likes> findPostLikes(int postId);

    @Query("select P.comments from Post P where P.id=?1")
    public List<Likes> findPostComment(int postId);


}
