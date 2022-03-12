package com.wfl.infrastructure.security;

import com.wfl.infrastructure.properties.CorsProperties;
import com.wfl.infrastructure.properties.SettingsProperties;
import com.wfl.infrastructure.security.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SettingsProperties settingsProperties;
    private final CorsProperties corsProperties;

    public WebSecurityConfig(
        SettingsProperties settingsProperties, CorsProperties corsProperties) {
        this.settingsProperties = settingsProperties;
        this.corsProperties = corsProperties;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/register", "/login").permitAll();

        //add our custom token-based authentication filter
        http.addFilterBefore(getJwtAuthenticationFilter(),
            UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthenticationFilter getJwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}
