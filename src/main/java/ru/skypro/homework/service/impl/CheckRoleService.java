package ru.skypro.homework.service.impl;

import org.springframework.security.core.*;
import org.springframework.stereotype.Component;

@Component
public class CheckRoleService {
    /**
     Determines whether the authenticated user is an admin or the owner of the Active Directory.
     @param authentication the authentication object representing the authenticated user
     @param ownerAd the username of the owner of the Active Directory
     @return true if the authenticated user is an admin or the owner of the Active Directory, false otherwise
     */
    public static boolean isAdminOrOwnerAd(Authentication authentication, String ownerAd) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        boolean isOwnerAd = authentication.getName().equals(ownerAd);

        return isAdmin || isOwnerAd;
    }
}