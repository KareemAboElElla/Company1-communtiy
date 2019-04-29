package com.example.community.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "vacancy")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)

public class Vacancy implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    @NotBlank
    private String descreption;
    @NotBlank
    private String posterID;
    @NotBlank
    private String posterName;
    @NotBlank
    private String title;
    @NotBlank
    private String requirments;
    @NotBlank
    private String benfits;
    @NotBlank
    private String type;
    @NotNull
    private int salary;
    @NotNull
    private int appid;
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
    public void setDescreption(String message){
        this.descreption=message;
    }
    public void setBenfits(String bef){
        this.benfits = bef;
    }
    public void setRequirments(String req){
        this.requirments = req;
    }
    public void setType(String ty){
        this.type = ty;
    }
    public void setTilte(String titl){
        this.title = titl;
    }
    public String getType(){ return this.type; }
    public void setSalary(int s){
        this.salary = s;
    }
    public int getSalary(){ return this.salary; }
    public void setAppid(int AID){
        this.appid = AID;
    }
    public int getAppid(){ return this.appid; }
    public String getBenfits(){ return this.benfits; }
    public String getRequirments(){
        return this.requirments;
    }
    public String getPosterName(){
        return this.posterName;
    }
    public String getPosterID(){
        return this.posterID;
    }
    public String getDescreption(){
        return this.descreption;
    }
    public String getTitle(){ return this.title; }
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


