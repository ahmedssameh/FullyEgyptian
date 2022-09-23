package com.example.fullyegyptian.Service;

import com.example.fullyegyptian.Model.*;
import com.example.fullyegyptian.PostRequest.EditCommentRequest;
import com.example.fullyegyptian.Repo.*;
import com.example.fullyegyptian.enums.Government;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PostService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PostRepo postRepo;

    @Autowired
    LikesRepo likesRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    SharedPostRepo sharedPostRepo;

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @Transactional
    public String creatPost(int userId,String gov, MultipartFile file) throws IOException {
        File reel=new File(FILE_DIRECTORY+file.getOriginalFilename());
        reel.createNewFile();
        FileOutputStream fos =new FileOutputStream(reel);
        fos.write(file.getBytes());
        fos.close();
        Post post=new Post();
        SharedPost sharedPost=new SharedPost();
        User user=userRepo.findUserByUid(userId);
        post.setPath(file.getOriginalFilename());
        post.setOwner(true);
        post.setOwnerId(userId);
        post.setDeleted(false);
        Government governmentEnum=Government.valueOf(gov);
        post.setGovernment(governmentEnum);
        sharedPost.setPost(post);
        sharedPost.setUser(user);
        sharedPostRepo.save(sharedPost);
        postRepo.save(post);
        return "Post is Created";
    }

    @Transactional
    public String postLike(int postId,int userId){
        Post post=postRepo.findPostById(postId);
        User user=userRepo.findUserByUid(userId);
        int noOfLikes;
        Likes like=new Likes();
        like.setLikeIt(true);
        like.setUser(user);
        if(post.getNoOfLike()!=0){
            if(isLiked(post.getLike(),userId))
                    return "You Can't Like Post Twice";

            }else{
            for(Likes l:post.getLike()){
                if(l.getUser().getId()==userId){
                    l.setLikeIt(true);
                    noOfLikes=post.getNoOfLike()+1;
                    post.setNoOfLike(noOfLikes);
                    postRepo.save(post);
                    return "You unliked the post before and now You Like it again ";
                }
            }
        }
        post.addLike(like);
        noOfLikes=post.getNoOfLike()+1;
        post.setNoOfLike(noOfLikes);
        postRepo.save(post);
        return "Your Like is submitted";
    }


    @Transactional
    public String undoPostLike(int postId,int userId){
        Post post=postRepo.findPostById(postId);
        User user=userRepo.findUserByUid(userId);
        int noOfLikes;
        if(post.getNoOfLike()!=0){
            if(!isLiked(post.getLike(),userId)){
                return "You already don't like this Post";
            }
            else if(post.getNoOfLike()!=0 && isLiked(post.getLike(),userId)){
                for(Likes l:post.getLike()){
                    if(l.getUser().getId()==userId){
                    l.setLikeIt(false);
                    noOfLikes=post.getNoOfLike()-1;
                    post.setNoOfLike(noOfLikes);
                    postRepo.save(post);
                    return "You undo your like ";
                    }
                }
        }
        }
        return "No Likes For this Post";
    }


   public boolean isLiked(List<Likes> likes, int userId){
        boolean flag=false;
        for(Likes l:likes){
            if(l.getUser().getId()==userId && l.isLikeIt()){
                flag=true;
                break;
            }
            else if(l.getUser().getId()==userId && !l.isLikeIt()){
                break;
            }
            else
                flag=true;
        }
        return flag;
    }


    @Transactional
    public String writeComment(int postId,int userId,String myComment){
        Post post=postRepo.findPostById(postId);
        User user=userRepo.findUserByUid(userId);
        int noOfComments;
        Comment comment=new Comment();
        comment.setComment(myComment);
        comment.setUser(user);
        post.addComment(comment);
        noOfComments=post.getNoOfComment()+1;
        post.setNoOfComment(noOfComments);
        commentRepo.save(comment);
        postRepo.save(post);
        return "Your Comment is submitted";
    }

    @Transactional
    public String editComment(EditCommentRequest editCommentRequest){
        Comment oldComment=commentRepo.findCommentById(editCommentRequest.getCommentId());
        oldComment.setComment(editCommentRequest.getNewComment());
        commentRepo.save(oldComment);
        return "Your comment is edited";
    }

    @Transactional
    public String deleteComment(EditCommentRequest editCommentRequest){
        Comment oldComment=commentRepo.findCommentById(editCommentRequest.getCommentId());
        oldComment.setDeleted(true);
        commentRepo.save(oldComment);
        return "Your comment is deleted";
    }

    @Transactional
    public String sharePost(int postId,int newUser){
        Post post=new Post();
        SharedPost sharedPost=new SharedPost();
        Post oldPost=postRepo.findPostById(postId);
        User user=userRepo.findUserByUid(newUser);
        post.setOwnerId(oldPost.getOwnerId());
        post.setPath(oldPost.getPath());
        post.setOwner(false);
        post.setGovernment(oldPost.getGovernment());
        sharedPost.setPost(post);
        sharedPost.setUser(user);
        sharedPostRepo.save(sharedPost);
        postRepo.save(post);
        return "You have shared a reel";
    }

    @Transactional
    public String deletePost(int postId){
        Post post=postRepo.findPostById(postId);
        post.setDeleted(true);
        postRepo.save(post);
        return "Your Post Is Deleted";
    }

    @Transactional
    public List<Post> filterByGov(String gov){
        Government government=Government.valueOf(gov);
        return postRepo.findGovById(government);
    }

    @Transactional
    public List<Post> getUserPosts(int userid){
        User user= userRepo.findUserByUid(userid);
        return sharedPostRepo.findUserPosts(user);
    }

    @Transactional
    public List<Post> getAllPosts(){
        return postRepo.findAll();
    }

    @Transactional
    public List<Likes> getPostLikes(int postId){
        return postRepo.findPostLikes(postId);
    }

    @Transactional
    public List<Likes> getPostComment(int postId){
        return postRepo.findPostComment(postId);
    }


}
