package com.example.fullyegyptian.Model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SharedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne
    Post post;

    @ManyToOne
    User user;
}
