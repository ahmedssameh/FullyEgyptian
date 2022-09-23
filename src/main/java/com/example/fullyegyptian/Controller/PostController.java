package com.example.fullyegyptian.Controller;

import com.example.fullyegyptian.Model.Likes;
import com.example.fullyegyptian.Model.Post;
import com.example.fullyegyptian.PostRequest.EditCommentRequest;
import com.example.fullyegyptian.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping(path="/createPost")
    public @ResponseBody String createPost(@RequestParam int userId,@RequestParam String gov,@RequestParam("File") MultipartFile file) throws IOException {

        String S = postService.creatPost(userId,gov,file);

        return S;
    }

    @PostMapping(path="/like")
    public @ResponseBody String like(@Valid @RequestParam int postId,@Valid @RequestParam int userId){

        String S = postService.postLike(postId,userId);

        return S;
    }

    @PutMapping(path="/undoLike")
    public @ResponseBody String undoLike(@Valid @RequestParam int postId,@Valid @RequestParam int userId){

        String S = postService.undoPostLike(postId,userId);

        return S;
    }

    @PostMapping(path="/comment")
    public @ResponseBody String comment(@Valid @RequestParam int postId,@Valid @RequestParam int userId,@RequestParam String myComment){

        String S = postService.writeComment(postId,userId,myComment);

        return S;
    }

    @PutMapping(path = "/editComment")
    public @ResponseBody String editComment(@Valid @RequestBody EditCommentRequest editCommentRequest){

        String S = postService.editComment(editCommentRequest);

        return S;
    }
    @PutMapping(path = "/deleteComment")
    public @ResponseBody String deleteComment(@Valid @RequestBody EditCommentRequest editCommentRequest){

        String S = postService.deleteComment(editCommentRequest);
        return S;
    }

    @PostMapping(path = "/share")
    public @ResponseBody String share(@Valid @RequestParam int postId,@Valid @RequestParam int userId){

        String S = postService.sharePost(postId,userId);
        return S;
    }

    @PutMapping(path = "/delete")
    public @ResponseBody String delete(@Valid @RequestParam int postId){
        String S = postService.deletePost(postId);
        return S;
    }

    @GetMapping(path = "/getFilteredPosts")
    public @ResponseBody List<Post> filterByGov(@Valid @RequestParam String gov){
        return postService.filterByGov(gov);
    }

    @GetMapping(path = "/getUserPosts")
    public @ResponseBody List<Post> filterByUser(@Valid @RequestParam int userId){
        return postService.getUserPosts(userId);
    }

    @GetMapping(path = "/getAllPosts")
    public @ResponseBody List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping(path = "/getPostLikes")
    public @ResponseBody List<Likes> getPostLikes(@RequestParam int postId){
        return postService.getPostLikes(postId);
    }

    @GetMapping(path = "/getPostComments")
    public @ResponseBody List<Likes> getPostComments(@RequestParam int postId){
        return postService.getPostComment(postId);
    }






}
