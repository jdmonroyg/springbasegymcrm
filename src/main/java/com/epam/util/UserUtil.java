package com.epam.util;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
@Component
public class UserUtil {

    private Set<String> userNames = new HashSet<>();

    public String generateRandomPassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int passwordLength = 10;
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.ints(passwordLength,0, characters.length())
                .mapToObj(characters::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    public String generateUsername(String firstName, String lastName, List<String> existingUsernames){
        String baseName = capitalize(firstName) + "." + capitalize(lastName);
        String username = baseName;
        int suffix=1;

        while (existingUsernames.contains(username)){
            username=baseName+suffix++;
        }
        return username;
    }

    private String capitalize(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
    }

    public void addUsernames(String username){
        userNames.add(username);
    }

    public List<String> getUserNames() {
        return new ArrayList<>(userNames);
    }
}
