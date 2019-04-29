/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.community.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Chaos Ruler
 */
@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
public class UserPost implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;
    @NotNull
    private int postPrivacy;
    @NotBlank
    private String content;
    @NotBlank
    private String posterId;
    @NotBlank
    private String posterName;
    @JsonInclude()
    @Transient
    private ArrayList<Comment> myComments;
    @Column(nullable = false)
    private int votesUp;
    @Column(nullable = false)
    private int votesDown;
    
    public void setPosterName(String postername){
        this.posterName=postername;
    }
    public void setPosterID(String posterid){
        this.posterId=posterid;
    }
    public void setContent(String message){
        this.content=message;
    }
    public void setPostPrivacy(int privacy){
        this.postPrivacy = privacy;        
    }
    public void setvote(int voice,int type){
        if (voice==1){
            this.votesUp++;
            if (type==0)
                this.votesDown--;
        }
        else if (voice ==0){
            this.votesDown++;
            if (type==0)
                this.votesUp--;
        }
    }
    
    public String getPosterName(){
        return this.posterName;
    }
    public String getPosterID(){
        return this.posterId;
    }
    public String getContent(){
        return this.content;
    }
    public int getPostPrivacy(){
        return this.postPrivacy;
    }
    public Long getID(){
        return this.postId;
    }
    public int getVotesUp(){
        return this.votesUp;
    }
    public int getVotesDown(){
        return this.votesDown;
    }
    public void setComments(ArrayList <Comment> newComments){
        for (Comment x :newComments)
            this.myComments.add(x);
    }
    public ArrayList <Comment> getMyComments(){
        return this.myComments;
    }
}
