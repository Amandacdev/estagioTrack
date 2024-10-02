package br.edu.ifpb.pweb2.estagiotrack.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public abstract class PasswordUtil {

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean checkPass(String plainPassword, String hashPassword) {
        if (BCrypt.checkpw(plainPassword, hashPassword))
            return true;
        else
            return false;
    }
}
