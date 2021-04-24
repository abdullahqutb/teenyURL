package com.urlShortner.Application.URLs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {
//    @AllowFiltering
    public Url findByShortURL(String short_url);
    public Iterable<Url> findAllByUserID(Long user_id);
}