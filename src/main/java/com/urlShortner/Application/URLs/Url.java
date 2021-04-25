package com.urlShortner.Application.URLs;

import javax.persistence.*;

@Entity
@Table(name = "urlDB")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "userID")
    private Integer userID;
    @Column(name = "longURL")
    private String longurl;
    @Column(name = "shortURL")
    private String shortURL;
    @Column(name = "createdat")
    private Long createdAt;

    public Url() {

    }

    public Url(Integer id, Integer userID, String longurl, String shortURL, Long createdAt) {
        this.id = id;
        this.userID = userID;
        this.longurl = longurl;
        this.shortURL = shortURL;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getLongURL() {
        return longurl;
    }

    public void setLongURL(String origURL) {
        this.longurl = origURL;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", userID=" + userID +
                ", longurl='" + longurl + '\'' +
                ", shortURL='" + shortURL + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
