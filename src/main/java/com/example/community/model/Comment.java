/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.community.model;



/**
 *
 * @author Chaos Ruler
 */

public class Comment {

    private Long commentId;
    private String owner;
    private String ownerId;
    private Long postId;
    private Long vacancyId;
    private String comment;
    
    public Comment(){}
    public void setCommentId(Long id){
        this.commentId=id;
    }
    public void setOwner(String owner){
        this.owner=owner;
    }
    public void setOwnerId(String ownerID){
        this.ownerId=ownerID;
    }
    public void setPostId(Long postID){
        this.postId=postID;
    }
    public void setVacancyId(Long vacancyID){
        this.vacancyId=vacancyID;
    }
    public void setContent(String com){
        this.comment=com;
    }
    public Long getCommentId(){
        return this.commentId;
    }
    public String getOwner(){
        return this.owner;
    }
    
    public String getOwnerId(){
        return this.ownerId;
    }
    
    public Long getPostId(){
        return this.postId;
    }
    
    public Long getVacancyId(){
        return this.vacancyId;
    }
    public String getComment(){
        return this.comment;
    }
}
