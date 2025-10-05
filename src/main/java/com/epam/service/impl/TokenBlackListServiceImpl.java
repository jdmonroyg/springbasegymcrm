package com.epam.service.impl;

import com.epam.service.TokenBlackListService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jdmon on 4/10/2025
 * @project springbasegymcrm
 */
@Service
public class TokenBlackListServiceImpl implements TokenBlackListService {

    private final Map<String, Instant> blacklist = new ConcurrentHashMap<>();

    @Override
    public void blackListToken(String token, Instant expiration) {
        blacklist.put(token, expiration);
    }

    @Override
    public boolean isBlackListed(String token) {
        Instant exp = blacklist.get(token);
        if (exp == null){
            return false;
        }
        if (exp.isBefore(Instant.now())){
            blacklist.remove(token);
            return false;
        }
        return true;
    }
}
