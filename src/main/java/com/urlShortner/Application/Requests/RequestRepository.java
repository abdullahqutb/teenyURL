package com.urlShortner.Application.Requests;

import java.util.UUID;

//import org.springframework.data.cassandra.repository.AllowFiltering;
//import org.springframework.data.cassandra.repository.CassandraRepository;

import com.urlShortner.Application.Requests.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
    public Iterable<Request> findAllByUrlID(Long UrlID);
}