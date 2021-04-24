package com.urlShortner.Application.Requests;

//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="urlID")
    private Long urlID;
    @Column(name="requestIP")
    private String requestIP;
    @Column(name="requestReferrer")
    private String requestReferrer;
    @Column(name="createdAt")
    private long createdAt;

    public Request(){

    }

    //public Request(UUID id, UUID urlID, String requestIP, String countryCode, String requestReferrer, long createdAt) {
    public Request(long id, long urlID, String requestIP, String requestReferrer, long createdAt) {
        this.id = id;
        this.urlID = urlID;
        this.requestIP = requestIP;
//        this.countryCode = countryCode;
        this.requestReferrer = requestReferrer;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUrlID() {
        return urlID;
    }

    public void setUrlID(long urlID) {
        this.urlID = urlID;
    }

    public String getRequestIP() {
        return requestIP;
    }

    public void setRequestIP(String requestIP) {
        this.requestIP = requestIP;
    }

    public String getRequestReferrer() {
        return requestReferrer;
    }

    public void setRequestReferrer(String requestReferrer) {
        this.requestReferrer = requestReferrer;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
