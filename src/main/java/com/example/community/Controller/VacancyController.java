package com.example.community.Controller;

import com.example.community.model.Vacancy;
import com.example.community.Exception.ResourceNotFoundException;
import com.example.community.model.Comment;
import com.example.community.repository.VacancyRepository;
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

@RestController
@RequestMapping("/posts/vacancy")
public class VacancyController {

    @Autowired
    VacancyRepository vacancyRepo;

    @GetMapping("/")
    public List<Vacancy> getAllPosts() throws SQLException {
        List<Vacancy> posts = vacancyRepo.findAll();
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/companyDB?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "");
        Statement stmt = conn.createStatement();
        for (Vacancy x : posts) {
            String strSelect = "select comment_id,owner,owner_id,post_id from comments where vacancy_id=" + x.getID() + ";";
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Comment> mycom = new ArrayList<>();
            while (rset.next()) {
                Comment next = new Comment();
                next.setCommentId(rset.getLong("comment_id"));
                next.setOwner(rset.getString("owner"));
                next.setOwnerId(rset.getString("owner_id"));
                next.setPostId(rset.getLong("vacancy_id"));
                mycom.add(next);
            }
            x.setComments(mycom);
        }
        return posts;
    }

    @PostMapping("/")
    public Vacancy createVacancy(@Valid @RequestBody Vacancy post) {
        return vacancyRepo.save(post);
    }

    @GetMapping("/postID/{postID}")
    public Vacancy getVacancyById(@PathVariable(value = "postID") Long postId) {
        return vacancyRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @PutMapping("/postid/{postid}/userid/{userid}")
    public Vacancy updatevacancy(@PathVariable(value = "postid") Long postId, @PathVariable(value = "userid") String userId, @Valid @RequestBody Vacancy postDetails) {
        Vacancy post = vacancyRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        if (post.getID().equals(userId)) {
            post.setDescreption(postDetails.getDescreption());
            post.setAppid(postDetails.getAppid());
            post.setBenfits(postDetails.getBenfits());
            post.setPosterID(postDetails.getPosterID());
            post.setPosterName(postDetails.getPosterName());
            post.setRequirments(postDetails.getRequirments());
            post.setTilte(postDetails.getTitle());
            post.setSalary(postDetails.getSalary());
            post.setType(postDetails.getType());
            Vacancy updatedvacancy = vacancyRepo.save(post);
            return updatedvacancy;
        }
        return null;
    }

    @DeleteMapping("/postid/{postid}/companyid/{companyid}")
    public ResponseEntity<?> deletevacancy(@PathVariable(value = "postid") Long postId, @PathVariable(value = "companyid") String companyId) {
        Vacancy post = vacancyRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        if (post.getPosterID().equals(companyId)) {
            vacancyRepo.delete(post);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/postid/{postid}/userid/{userid}/vote/{vote}")
    public Vacancy VoteToVacancy(@PathVariable(value = "postid") Long postId, @PathVariable(value = "userid") String userId, @PathVariable(value = "vote") int vote, @Valid @RequestBody Vacancy postDetails) throws SQLException {
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/companyDB?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "");
        Statement stmt = conn.createStatement();
        String strSelect = "select * votes  where user_id = '" + userId +"' Vacancy_id = '"+postId+ "' ;";
        ResultSet exist = stmt.executeQuery(strSelect);
        int type;
        if (exist.next()) {
            strSelect = "update votes  set type = '"+vote+"' where user_id = '" + userId +"' vacancy_id = '"+ postId+"' ;";
            exist=stmt.executeQuery(strSelect);
            type=0;
        }
        else {
            strSelect = "insert into (votes type,vacancy_id,user_id) values ('" + vote + "','" + postId + "','" + userId + "'); ";
            exist = stmt.executeQuery(strSelect);
            type=1;
        }
        Vacancy post = vacancyRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        post.setvote(vote,type);
        Vacancy updatedVacancy = vacancyRepo.save(post);
        return updatedVacancy;
    }
    
    @PostMapping("/comment")
    public String AddComment(@RequestBody Comment com) throws SQLException {
        try{ 
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/companyDB?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "");
        Statement stmt = conn.createStatement();
        String strSelect =  "insert into comments (owner, vacancy_id, owner_id,content) values ('" + com.getOwner() + "','" + com.getVacancyId() + "','" + com.getOwnerId() + "','"+com.getComment()+"'); ";
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
