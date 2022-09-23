package com.example.fullyegyptian.Repo;

import com.example.fullyegyptian.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    @Query("select U from User U where U.id=?1")
    public User findUserByUid(int uid);

    @Query("select U from User U where U.Username=?1")
    public User findUserByName(String username);
}
