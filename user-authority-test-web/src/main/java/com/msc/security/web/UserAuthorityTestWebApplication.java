package com.msc.security.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages =  {
        "com.msc.security.web",
        "com.msc.security.user"

})
public class UserAuthorityTestWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAuthorityTestWebApplication.class, args);
    }

}
