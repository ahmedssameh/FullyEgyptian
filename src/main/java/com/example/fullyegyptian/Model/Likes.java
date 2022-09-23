package com.example.fullyegyptian.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;


@AllArgsConstructor @NoArgsConstructor @Setter @Getter
@Entity
public class Likes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int likeId;


    @OneToOne(cascade = {CascadeType.ALL})
    @Autowired
     User user;


     boolean likeIt;






}
