package com.example.fullyegyptian.PostRequest;


import lombok.Data;

@Data
public class EditCommentRequest {
    String newComment;
    /*int userId;
    int postId;*/
    int commentId;
}
