package com.urlShortner.Application.URLs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<Url, Integer> {
//    @AllowFiltering
    public Url findByShortURL(String shorturl);
//    public Iterable<Url> findAllByUserID(Long userid);
}