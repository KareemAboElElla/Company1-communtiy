/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.community.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
@SecondaryTable(name = "comments", pkJoinColumns = @PrimaryKeyJoinColumn(name = "post_id"))
@SecondaryTable(name = "votes", pkJoinColumns = @PrimaryKeyJoinColumn(name = "post_id"))
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)

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
    public void setvote(int voice){
        if (voice==1){
            this.votesUp++;
        }
        else if (voice ==0){
            this.votesDown++;
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
}
