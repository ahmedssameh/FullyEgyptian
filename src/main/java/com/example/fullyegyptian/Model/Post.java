package com.example.fullyegyptian.Model;

import com.example.fullyegyptian.enums.Government;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;


    @Autowired
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Likes> like=new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Comment> comments=new ArrayList<>();

    @Column(columnDefinition = "integer default 0")
    int noOfLike;

    @Column(columnDefinition = "integer default 0")
    int noOfComment;

   /* @ManyToMany(cascade = {CascadeType.ALL})
    List<User> users=new ArrayList<>();*/

    boolean isOwner;

    int ownerId;

    @NotNull
    private String Path;

    @Column(columnDefinition = "boolean default false")
    boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Government government;

    public void addLike(Likes likeMe){
        like.add(likeMe);
    }

    public void addComment(Comment userComment){
        comments.add(userComment)
    ;
    }

//    public void addUser(User user){
//        users.add(user);
//    }


}
