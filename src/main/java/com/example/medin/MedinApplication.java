package com.example.medin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MedinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedinApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedHeaders("*")
                        .allowedOriginPatterns("http://localhost:3000")
                        .allowedMethods("*");
            }
        };
    }
//    @Autowired
//    private EmailSenderService senderService;
//    @EventListener(ApplicationReadyEvent.class)
//    public void sendMail(){
//        senderService.sendEmail("ftkhlla1@gmail.com","Reset password", "Your password is admin");
//
//    }

}
