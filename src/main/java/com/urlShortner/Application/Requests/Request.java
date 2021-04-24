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
    private Long id;

    @Column(name="urlID")
    private Long urlID;
    @Column(name="requestIP")
    private String requestIP;
    @Column(name="requestReferrer")
    private String requestReferrer;
    @Column(name="createdAt")
    private Long createdAt;

    public Request(){

    }

    //public Request(UUID id, UUID urlID, String requestIP, String countryCode, String requestReferrer, long createdAt) {
    public Request(Long id, Long urlID, String requestIP, String requestReferrer, Long createdAt) {
        this.id = id;
        this.urlID = urlID;
        this.requestIP = requestIP;
//        this.countryCode = countryCode;
        this.requestReferrer = requestReferrer;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUrlID() {
        return urlID;
    }

    public void setUrlID(Long urlID) {
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
