package com.example.fullyegyptian.Repo;

import com.example.fullyegyptian.Model.Post;
import com.example.fullyegyptian.Model.SharedPost;
import com.example.fullyegyptian.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharedPostRepo extends JpaRepository<SharedPost,Integer> {

    @Query("select P.post from SharedPost P where P.user=?1")
    public List<Post> findUserPosts(User user);

}
