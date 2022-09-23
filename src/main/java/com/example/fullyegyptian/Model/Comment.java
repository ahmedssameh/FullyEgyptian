package com.example.fullyegyptian.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int commentId;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @Autowired
    User user;

    String comment;

    @Column(columnDefinition = "boolean default false")
    boolean deleted;



}
