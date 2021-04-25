package com.urlShortner.Application;

import com.urlShortner.Application.URLs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public class MainController {

    final long value = 1000L;

    @Autowired
    private UrlRepository urLsRepository;

    public MainController() {
    }

    @RequestMapping("/{variable}")
    public @ResponseBody int redirect(@PathVariable(value="variable") String shorturl) {
        System.out.println(shorturl);
        if(shorturl != null) {
            Url result = urLsRepository.findByShortURL(shorturl);
            if (result != null) {

                String orig_url = result.getLongURL();
                System.out.println(orig_url);
                if (!orig_url.startsWith("http://") && !orig_url.startsWith("https://"))
                    orig_url = "http://" + orig_url;

//                result = urLsRepository.save(result);

                System.out.println(result.toString());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", orig_url);
//                return new ResponseEntity<String>(headers,HttpStatus.PERMANENT_REDIRECT);
                return 0;
            } else {
                return 1;
//                return new ResponseEntity<>("Link does not exist.", HttpStatus.NOT_FOUND);
            }
        } else {
            return 2;
//            return new ResponseEntity<>("Hello.", HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @Controller
    public class LoginController {
        @GetMapping("/")
        public String test(Model model){
            return "index";
        }
    }

    @Controller
    public class RegisterController {
        @RequestMapping("/Register")
        public String test(Model model){
            return "register";
        }
    }
}
