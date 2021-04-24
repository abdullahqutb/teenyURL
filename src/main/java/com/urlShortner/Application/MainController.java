package com.urlShortner.Application;

import com.urlShortner.Application.Requests.*;
import com.urlShortner.Application.URLs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Autowired
    private RequestRepository requestsRepository;

    public MainController() {
    }

    @GetMapping("/{pathVariable}")
    public ResponseEntity<?> redirect(@PathVariable("pathVariable")String link, HttpServletRequest request) {
        if(link != null) {
            Url result = urLsRepository.findByShortURL(link);
            if (result != null) {
                if (result.getExpiresAt() > System.currentTimeMillis() / value) {
                    String orig_url = result.getOrigURL();
                    if (!orig_url.startsWith("http://") && !orig_url.startsWith("https://"))
                        orig_url = "http://" + orig_url;

                    result = urLsRepository.save(result);

                    // Get Referer
                    String referer = request.getHeader("referer");

                    // Record the new request.
                    addNewRequest(result.getId(),  request.getRemoteAddr(), referer);

                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Location", orig_url);
                    return new ResponseEntity<String>(headers,HttpStatus.PERMANENT_REDIRECT);
                } else {
                    return new ResponseEntity<>("Link has been expired.", HttpStatus.NOT_FOUND);
                }
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

    boolean addNewRequest(Long url_id, String request_ip, String request_referrer) {
        Request new_request = new Request();
        new_request.setUrlID(url_id);
        new_request.setRequestIP(request_ip);
        new_request.setRequestReferrer(request_referrer);
        new_request.setCreatedAt(System.currentTimeMillis() / 1000L);

        try {
            requestsRepository.save(new_request);
        } catch (DataIntegrityViolationException e) {
            return false;
        }

        return true;

    }
}
