package com.example.fullyegyptian.Repo;

import com.example.fullyegyptian.Model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepo extends JpaRepository<Likes,Integer> {

    @Query("select L from Likes L where L.user.id=?1")
    public List<Likes> findLikeByUserId(int id);
}
