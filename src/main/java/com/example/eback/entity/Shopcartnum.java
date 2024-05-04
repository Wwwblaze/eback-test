package com.example.eback.entity;


import javax.persistence.*;

@Entity
@Table(name = "shopcartnum")
public class Shopcartnum {
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "userid")
    private int userId;

    @Basic
    @Column(name = "bookid")
    private int bookId;

    @Basic
    @Column(name = "num")
    private int num;


    public Shopcartnum(int userId, int bookId, int num){
        this.userId = userId;
        this.bookId = bookId;
        this.num = num;
    }

    public Shopcartnum() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
