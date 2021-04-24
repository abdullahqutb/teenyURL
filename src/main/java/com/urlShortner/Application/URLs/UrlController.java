package com.urlShortner.Application.URLs;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UrlController {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static SecureRandom random = new SecureRandom();

    @Autowired
    private UrlRepository urlRepository;

    @PostMapping(path = "/Url")
    public @ResponseBody Url addNewURL(@RequestBody Url url) {
        if (url.getOrigURL().equals("") || url.getOrigURL().trim().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given URL is empty.");
        }

        Url new_url = new Url();
        new_url.setId(Uuids.timeBased());
        new_url.setOrigURL(url.getOrigURL());
        new_url.setCreatedAt(System.currentTimeMillis() / 1000L);
        new_url.setUserID(url.getUserID());
        new_url.setShortURL(generateURL(10));

        if (url.getExpiresAt() == 0){
//           URL Expires after 2 days
            new_url.setExpiresAt((System.currentTimeMillis()/ 1000L) + (86400 * 2));
        } else {
            new_url.setExpiresAt(url.getExpiresAt());
        }

        try {
            new_url = urlRepository.save(new_url);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "The URL could not be registered");
        }
        return new_url;
    }

    public static String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }

    private String generateURL(int len) {
        String short_url = generateRandomString(len);
//        Check if current URL already exists in database
        if (urlRepository.findByShortURL(short_url) != null) {
//            Check until a unique short URL is generated
            while (urlRepository.findByShortURL(short_url) != null) {
                short_url = generateRandomString(len);
            }
        }
        return short_url;
    }
}