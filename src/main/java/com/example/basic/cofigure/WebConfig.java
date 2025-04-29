package com.example.basic.cofigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/api/**").allowedOrigins("https://tubi55.github.io").allowedMethods("GET","POST","PUT","DELETE").allowCredentials(true);
    }
}
