/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.community.Controller;

import com.example.community.model.UserPost;
import com.example.community.model.Comment;
import com.example.community.repository.UserPostRepository;
import com.example.community.Exception.ResourceNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author Chaos Ruler
 */
@RestController
@RequestMapping("/posts/userposts")
public class UserPostController {

    @Autowired
    UserPostRepository userPostRepo;

    @GetMapping("/userid/{userid}") // testing
    public List<UserPost> getAllPosts(@PathVariable(value = "userid") String userId) throws SQLException {
        List<UserPost> posts = userPostRepo.findAll();
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/companyDB?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "");

        Statement stmt = conn.createStatement();
        Statement stmt2 = conn.createStatement();
        String strSelect = "select followed_id from followers where user_id = '" + userId + "';";
        ResultSet results = stmt2.executeQuery(strSelect);
        ArrayList<String> follows = new ArrayList<>();
        follows.add(userId);
        while (results.next()) {
            follows.add(results.getString("followed_id"));
        }
        for (int i=0;i< posts.size();i++) {
            if (posts.get(i).getPostPrivacy() == 0 || (posts.get(i).getPostPrivacy()==1 && follows.contains(posts.get(i).getPosterID()))) {
                String strSelect2 = "select comment_id,owner,owner_id,post_id from comments where post_id=" + posts.get(i).getID() + ";";
                ResultSet rset = stmt.executeQuery(strSelect2);
                ArrayList<Comment> mycom = new ArrayList<>();
                while (rset.next()) {
                    Comment next = new Comment();
                    next.setCommentId(rset.getLong("comment_id"));
                    next.setOwner(rset.getString("owner"));
                    next.setOwnerId(rset.getString("owner_id"));
                    next.setPostId(rset.getLong("post_id"));
                    mycom.add(next);
                }
                posts.get(i).setComments(mycom);
            }
            else {
                posts.remove(i);
            }
        }
        if (posts.size()==0)
            return null;
        else
            return posts;
    }

    @PostMapping("/")// working
    public UserPost createPost(@Valid @RequestBody UserPost post) {
        return userPostRepo.save(post);
    }

    @GetMapping("/search/userID/{userID}/postID/{postID}") //working
    public UserPost getUserPostById(@PathVariable(value = "postID") Long postId, @PathVariable(value = "userID") String userId) {
        return userPostRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }
    
    @PutMapping("/postid/{postid}/userID/{userID}/content/{content}") //working
    public UserPost updatePost(@PathVariable(value = "postid") Long postId,
            @PathVariable(value = "userID") String userId,@PathVariable(value = "content") String content) {
        UserPost post = userPostRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        
        if (post.getPosterID().equals(userId)) {
            post.setContent(content);
            UserPost updatedNote = userPostRepo.save(post);
            return updatedNote;
        }
        return null;
    }

    @PutMapping("/postid/{postid}/userid/{userid}/vote/{vote}")// working
    public UserPost VoteToPost(@PathVariable(value = "postid") Long postId, @PathVariable(value = "userid") String userid, @PathVariable(value = "vote") int vote) throws SQLException {

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/companyDB?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "");
        Statement stmt = conn.createStatement();
        String strSelect = "select * from votes  where user_id = '" + userid +"' and post_id = '"+postId+ "' ;";
        ResultSet exist = stmt.executeQuery(strSelect);
        int type;
        if (exist.next()) {
            strSelect = "update votes  set type = "+vote+" where user_id = '" + userid +"'and  post_id = '"+ postId+"' ;";
            stmt.executeUpdate(strSelect);
            type=0;
        }
        else {
            strSelect = "insert into votes (type, post_id, user_id) values ('" + vote + "','" + postId + "','" + userid + "'); ";
            stmt.executeUpdate(strSelect);
            type=1;
        }
        UserPost post = userPostRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        post.setvote(vote,type);
        UserPost updatedPost = userPostRepo.save(post);
        return updatedPost;
    }

    @DeleteMapping("/postid/{postid}/userid/{userid}")
    public ResponseEntity<?> deletepost(@PathVariable(value = "postid") Long postId, @PathVariable(value = "userid") String userId) {
        UserPost post = userPostRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        if (post.getPosterID().equals(userId)) {
            userPostRepo.delete(post);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/comment")
    public String AddComment(@RequestBody Comment com) throws SQLException {
        try{ 
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/companyDB?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "");
        Statement stmt = conn.createStatement();
        String strSelect =  "insert into comments (owner, post_id, owner_id,content) values ('" + com.getOwner() + "','" + com.getPostId() + "','" + com.getOwnerId() + "','"+com.getComment()+"'); ";
            stmt.executeUpdate(strSelect);
            return "Comment added Sucessfully";
        }catch(Exception e){
            return "faild to add comment: " +e;
        }
    }
    
    @PutMapping("/comment/commentid/{commentid}/ownerid/{ownerid}/content/{content}")
    public String UpdateComment(@PathVariable (value="commentid")Long commentID,@PathVariable (value="ownerid")String ownerID,@PathVariable (value="content")String Content){
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/companyDB?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "");
        Statement stmt = conn.createStatement();
        String strSelect =  "update comments  set content = "+Content+" where owner_id = '" + ownerID +"'and  comment_id = '"+ commentID+"' ;";
            stmt.executeUpdate(strSelect);
            return "Comment updted successfully";
        }catch(Exception e){
            return "comment is NOT updated : "+e;
        }
    }
    
    @PostMapping("/follow/userid/{userid}/followedid/{followedid}")
    public void follow (@PathVariable (value="userid")String userID,@PathVariable (value="followedid")String followedID){
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/companyDB?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "");
        Statement stmt = conn.createStatement();
        String strSelect =  "insert into followers (user_id, followed_id) values ('" + userID + "','" + followedID +"'); ";
            stmt.executeUpdate(strSelect);
        }catch(Exception e){}
    }
}
