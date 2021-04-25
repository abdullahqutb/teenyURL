package com.urlShortner.Application.URLs;

import com.urlShortner.Application.URLs.Url;

import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.urlShortner.Application.Users.User;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;


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

//    @GetMapping("/Url")
//    public String getAllUrls(@RequestBody User user) {
//        System.out.println("hello");
//        return "HELLO";
//    }

    @GetMapping("/Url/{id}")
    public List<Url> getAllUrl(@PathVariable("id")Integer id){
        System.out.println(id);

        List<Url> urls = (List<Url>) urlRepository.findAll();
        List<Url> results = new ArrayList<>();
        for (Url url : urls) {
            if(url.getUserID() == id){
                results.add(url);
            }
        }
        return results;
    }

    @GetMapping("/Url/all")
    public List<Url> getUrls(){
        //returns all the urls
        List<Url> urls = (List<Url>) urlRepository.findAll();

        return urls;
    }

    @PostMapping("/Url/freq")
    public Integer getFreqUrl(@RequestBody Url searchUrl){
        //gets the frequency of the shortURL given
        Url temp = urlRepository.findByShortURL(searchUrl.getShortURL());
        if(temp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Given shortUrl does not exists!");
        }
        else{
            return temp.getFrequency();
        }
    }

    @PostMapping("/Url")
    public @ResponseBody Url addNewURL(@RequestBody Url url) {
        if (url.getLongURL().equals("") || url.getLongURL().trim().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given URL is empty.");
        }

        Url new_url = new Url();
        new_url.setLongURL(url.getLongURL());
        new_url.setCreatedAt(System.currentTimeMillis() / 1000L);
        System.out.println(new_url.getCreatedAt());
        new_url.setUserID(url.getUserID());
        new_url.setShortURL(generateURL(10));
        new_url.setFrequency(0);
        System.out.println(new_url.getShortURL());

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

    @PostMapping("/Url/Custom")
    public @ResponseBody Url addCustomUrl(@RequestBody Url url) {

        if (url.getLongURL().equals("") || url.getLongURL().trim().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given URL is empty.");
        }

        if (urlRepository.findByShortURL(url.getShortURL()) != null){
            System.out.println("error shorturl");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given URL is not unique.");
        }

        Url new_url = new Url();
        new_url.setLongURL(url.getLongURL());
        new_url.setCreatedAt(System.currentTimeMillis() / 1000L);
        new_url.setUserID(url.getUserID());
        new_url.setShortURL(url.getShortURL());
        new_url.setFrequency(0);
        System.out.println(new_url.getShortURL());

        try {
            System.out.println("saving");
            new_url = urlRepository.save(new_url);
        } catch (DataIntegrityViolationException e) {
            System.out.println("NOT saving");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "The URL could not be registered");
        }
        return new_url;
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