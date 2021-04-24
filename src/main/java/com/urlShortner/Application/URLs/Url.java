package com.urlShortner.Application.URLs;

//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;
import javax.persistence.*;

@Entity
@Table(name = "url")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "userID")
    private Long userID;
    @Column(name = "origURL")
    private String origURL;
    @Column(name = "shortURL")
    private String shortURL;
    @Column(name = "createdAt")
    private Long createdAt;
    @Column(name = "expiresAt")
    private Long expiresAt;
//    private String creatorIP;
//    private int visitorCount;
//    private int visitorLimit = 0;
//    private Boolean isCustom = false;

    public Url() {

    }

    public Url(Long id, Long userID, String origURL, String shortURL, Long createdAt, Long expiresAt) {
        this.id = id;
        this.userID = userID;
        this.origURL = origURL;
        this.shortURL = shortURL;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
//        this.creatorIP = creatorIP;
//        this.visitorCount = visitorCount;
//        this.visitorLimit = visitorLimit;
//        this.isCustom = isCustom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getOrigURL() {
        return origURL;
    }

    public void setOrigURL(String origURL) {
        this.origURL = origURL;
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

    public Long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }
//
//    public String getCreatorIP() {
//        return creatorIP;
//    }
//
//    public void setCreatorIP(String creatorIP) {
//        this.creatorIP = creatorIP;
//    }
//
//    public int getVisitorCount() {
//        return visitorCount;
//    }
//
//    public void setVisitorCount(int visitorCount) {
//        this.visitorCount = visitorCount;
//    }
//
//    public int getVisitorLimit() { return visitorLimit; }
//
//    public void setVisitorLimit(int visitorLimit) { this.visitorLimit = visitorLimit; }
//
//    public Boolean getCustom() { return isCustom; }
//
//    public void setCustom(Boolean custom) { isCustom = custom; }

    @Override
    public String toString() {
        return "URL{" +
                "id=" + id +
                ", userID=" + userID +
                ", origURL='" + origURL + '\'' +
                ", shortURL='" + shortURL + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
//                ", creatorIP='" + creatorIP + '\'' +
//                ", visitorCount=" + visitorCount +
//                ", visitorLimit=" + visitorLimit +
//                ", isCustom=" + isCustom +
                '}';
    }
}
