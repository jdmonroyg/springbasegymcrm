package com.epam.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.epam.util.Constants.REGEX_PASSWORD;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 11/09/2025
 * @project springbasegymcrm
 */


class UserUtilTest {

    private final UserUtil userUtil = new UserUtil();

    @Test
    @DisplayName("Checking password size")
    void generateRandomPassword_returnsLength10(){
        String pwd = userUtil.generateRandomPassword();
        assertEquals(10, pwd.length());
    }

    @RepeatedTest(5)
    @DisplayName("Checking random password pattern")
    void generateRandomPassword_onlyValidCharacters(){
        String pwd = userUtil.generateRandomPassword();
        assertTrue(pwd.matches(REGEX_PASSWORD));
    }

    @RepeatedTest(5)
    @DisplayName("Checking two passwords with different values")
    void generateRandomPassword_producesDifferentValues() {
        String first = userUtil.generateRandomPassword();
        String second = userUtil.generateRandomPassword();
        assertNotEquals(first, second);
    }

    @Test
    @DisplayName("Checking the username for the first time")
    void generateUsername_noConflict() {
        String username = userUtil.generateUsername("juan", "pérez", Set.of());
        assertEquals("Juan.Pérez", username);
    }

    @Test
    @DisplayName("conflict twice username")
    void generateUsername_conflictTwice() {
        Set<String> existing = Set.of("Juan.Perez","Juan.Perez1");
        String username = userUtil.generateUsername("juan", "perez", existing);
        assertEquals("Juan.Perez2", username);
    }



}