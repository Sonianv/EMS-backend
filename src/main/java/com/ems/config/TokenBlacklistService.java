package com.ems.config;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.synchronizedSet;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final JwtService jwtService;

    private Set<String> blacklistedTokens = synchronizedSet(new HashSet<>());

    public void blacklist(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    @Scheduled(fixedRate = 3600000)
    public void purgeExpiredTokens() {
        blacklistedTokens.removeIf(jwtService::isTokenExpired);
    }
}
