package com.epam.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
@Component
public class UserUtil {

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
}
