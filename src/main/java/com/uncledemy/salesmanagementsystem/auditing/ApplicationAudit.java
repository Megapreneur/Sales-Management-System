package com.uncledemy.salesmanagementsystem.auditing;

import com.uncledemy.salesmanagementsystem.model.User;
import com.uncledemy.salesmanagementsystem.security.config.SecureUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAudit implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext().getAuthentication();
        if (authentication == null ||
        !authentication.isAuthenticated() ||
        authentication instanceof AnonymousAuthenticationToken){
            return Optional.empty();
        }
        SecureUser userPrincipal = (SecureUser) authentication.getPrincipal();
        return Optional.of(userPrincipal.getUserId());
    }
}
