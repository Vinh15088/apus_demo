package com.apus.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new AuditorAware<Long>() {
            @Override
            public Optional<Long> getCurrentAuditor() {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//                if (authentication == null || !authentication.isAuthenticated()) {
//                    return Optional.of(0L);
//                }
//
//                try {
//                    Object principal = authentication.getPrincipal();
//                    if (principal instanceof UserPrincipal) {
//                        return Optional.of(((UserPrincipal) principal).getId());
//                    }
//                    return Optional.of(0L);
//                } catch (Exception e) {
//                    return Optional.of(0L);

                return Optional.of(0L);
            }
        };
    }
}
