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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    @NotBlank
    private int postState;
    @NotBlank
    private String content;
    @NotBlank
    private String posterID;
    @NotBlank
    private String posterName;
    
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date postDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
    
    private int votesUp;
    private int votesDown;
    public void setPosterName(String postername){
        this.posterName=postername;
    }
    public void setPosterID(String posterid){
        this.posterID=posterid;
    }
    public void setContent(String message){
        this.content=message;
    }
    public void setState(int privacy){
        this.postState = privacy;        
    }
    public String getPosterName(){
        return this.posterName;
    }
    public String getPosterID(){
        return this.posterID;
    }
    public String getContent(){
        return this.content;
    }
    public int getState(){
        return this.postState;
    }
    public Long getID(){
        return this.postID;
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
