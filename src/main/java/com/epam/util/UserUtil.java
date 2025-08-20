package com.epam.util;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author jdmon on 27/07/2025
 * @project springbasegymcrm
 */
@Component
public class UserUtil {

    private Set<String> userNames = new HashSet<>();

    public String generateRandomPassword(){
        int passwordLength = 10;
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.ints(passwordLength,0, VALID_CHAR_CODES.size())
                .mapToObj(VALID_CHAR_CODES::get)
                .map(code -> (char) code.intValue())
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private static final List<Integer> VALID_CHAR_CODES =
            Stream.concat(
                    IntStream.rangeClosed('0','9').boxed(),
                    Stream.concat(
                            IntStream.rangeClosed('A', 'Z').boxed(),
                            IntStream.rangeClosed('a', 'z').boxed()
                    )
            ).toList();

    public String generateUsername(String firstName, String lastName, Set<String> existingUsernames){
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

    public Set<String> getUserNames() {
        return new HashSet<>(userNames);
    }
}
