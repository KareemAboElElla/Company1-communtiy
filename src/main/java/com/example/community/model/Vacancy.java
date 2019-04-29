package com.example.community.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "vacancy")
@SecondaryTable(name = "comments", pkJoinColumns = @PrimaryKeyJoinColumn(name = "vacancy_id"))
@SecondaryTable(name = "votes", pkJoinColumns = @PrimaryKeyJoinColumn(name = "vacancy_id"))
@EntityListeners(AuditingEntityListener.class)

public class Vacancy implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacancyId;
    @NotBlank
    private String descreption;
    @NotBlank
    private String companyId;
    @NotBlank
    private String companyName;
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

    private int votesUp;
    private int votesDown;
    @JsonInclude()
    @Transient
    private ArrayList<Comment> myComments;
    public void setPosterName(String postername){
        this.companyName=postername;
    }
    public void setPosterID(String posterid){
        this.companyId=posterid;
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
        return this.companyName;
    }
    public String getPosterID(){
        return this.companyId;
    }
    public String getDescreption(){
        return this.descreption;
    }
    public String getTitle(){ return this.title; }
    public Long getID(){
        return this.vacancyId;
    }
    public int getVotesUp(){
        return this.votesUp;
    }
    public int getVotesDown(){
        return this.votesDown;
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
    public void setComments(ArrayList <Comment> newComments){
        for (Comment x :newComments)
            this.myComments.add(x);
    }
    public ArrayList <Comment> getMyComments(){
        return this.myComments;
    }
}


