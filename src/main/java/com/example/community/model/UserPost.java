/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.community.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Chaos Ruler
 */
@Entity
@Table(name = "post")
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
    
    @Column(nullable = false, updatable = false, columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date postDate;

    @Column(nullable = false, columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
    
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
    public void setState(int privacy){
        this.postPrivacy = privacy;        
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
    public int getState(){
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
    public Date getUpdateDate(){
        return this.updatedAt; 
    }
    public Date getPostDate(){
        return this.postDate; 
    }
}
