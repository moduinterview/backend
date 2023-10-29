package com.moduinterview.user.utility;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

  public static String getEncodedPassword(String password) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.encode(password);
  }

  public static boolean isEqualPassword(String password, String encryptedPassword) {
    return BCrypt.checkpw(password, encryptedPassword);
  }

}
