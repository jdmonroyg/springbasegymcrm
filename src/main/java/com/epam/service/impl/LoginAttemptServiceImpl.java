package com.epam.service.impl;

import com.epam.service.LoginAttemptService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jdmon on 1/10/2025
 * @project springbasegymcrm
 */
@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private static final int MAX_ATTEMPTS = 3;
    private static final Duration LOCK_TIME = Duration.ofMinutes(5);

    private final Map<String, AttemptRecord> attemptsCache = new ConcurrentHashMap<>();

    @Override
    public void loginSucceeded(String username) {
        attemptsCache.remove(username);
    }

    @Override
    public void loginFailed(String username) {
        AttemptRecord oldRecord = attemptsCache.get(username);
        int newCount;

        if (oldRecord!=null && isLockTimeExpired(oldRecord.lastAttempt)) {
            newCount = 1;                        
        } else {
            newCount = (oldRecord != null) ? oldRecord.count() +1 : 1;
        }
        attemptsCache.put(username, new AttemptRecord(newCount, Instant.now()));
    }

    @Override
    public boolean isBlocked(String username) {
        AttemptRecord record = attemptsCache.get(username);
        if (record == null){
            return false;
        }

        if (isLockTimeExpired(record.lastAttempt)){
            attemptsCache.remove(username);
            return false;
        }

        return record.count >= MAX_ATTEMPTS;
    }

    private boolean isLockTimeExpired(Instant lastAttempt){
        return Duration.between(lastAttempt, Instant.now())
                .compareTo(LOCK_TIME)>0;
    }

    private record AttemptRecord(int count, Instant lastAttempt) {}
}
