package com.urlShortner.Application;

import com.urlShortner.Application.URLs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

public class MainController {

    final long value = 1000L;

    @Autowired
    private UrlRepository urLsRepository;

    public MainController() {
    }

    @GetMapping("/{pathVariable}")
    public ResponseEntity<?> redirect(@PathVariable("pathVariable")String link, HttpServletRequest request) {
        if(link != null) {
            Url result = urLsRepository.findByShortURL(link);
            if (result != null) {

                String orig_url = result.getOrigURL();
                if (!orig_url.startsWith("http://") && !orig_url.startsWith("https://"))
                    orig_url = "http://" + orig_url;

                result = urLsRepository.save(result);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", orig_url);
                return new ResponseEntity<String>(headers,HttpStatus.PERMANENT_REDIRECT);
            } else {
                return new ResponseEntity<>("Link does not exist.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Hello.", HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> hello() {
        System.out.println("Hello");
        return new ResponseEntity<>("API is running.", HttpStatus.OK);
    }
}
