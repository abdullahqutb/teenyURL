package com.urlShortner.Application;

import com.urlShortner.Application.URLs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @Autowired
    private UrlRepository urLsRepository;

    public MainController() {
    }

    @GetMapping("/{pathVariable}")
    public ResponseEntity<?> redirect(@PathVariable("pathVariable")String link) {
        System.out.println("REDIRECT METHOD");
        System.out.println(link);
        if(link != null) {
            Url result = urLsRepository.findByShortURL(link);
            if (result != null) {

                String orig_url = result.getLongURL();
                System.out.println(result.getFrequency());
                result.setFrequency(result.getFrequency() + 1);
                System.out.println(orig_url);

                result = urLsRepository.save(result);
                System.out.println(result.toString());

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
}
