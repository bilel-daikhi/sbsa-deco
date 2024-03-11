package org.sbsa.deco.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev","prod"})
public class FakerConfig {
    @Bean
    public Faker faker() {
        return new Faker();
    }
}
