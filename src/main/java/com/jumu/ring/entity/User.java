package com.jumu.ring.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/8.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String userName;

    private String nickName;//用户昵称

    private String followIntegral;//关注积分

    private String buyIntegral;//购买积分

    private int followPost;//关注积分提现资格


    @CreationTimestamp
    private Date created;

    public void setFollowPost(int followPost) {
        this.followPost = followPost;
    }

    public int getFollowPost() {
        return followPost;
    }

    public String getBuyIntegral() {
        return buyIntegral;
    }

    public void setBuyIntegral(String buyIntegral) {
        this.buyIntegral = buyIntegral;
    }

    public void setFollowIntegral(String followIntegral) {
        this.followIntegral = followIntegral;
    }

    public String getFollowIntegral() {
        return followIntegral;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
