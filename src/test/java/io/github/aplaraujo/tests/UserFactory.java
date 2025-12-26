package io.github.aplaraujo.tests;

import io.github.aplaraujo.entities.Role;
import io.github.aplaraujo.entities.User;
import io.github.aplaraujo.entities.enums.RoleEnum;

public class UserFactory {
    public static User createUser() {
        User user = new User(1L, "Isis", "Dias", "isis_dias@iclud.com", "$2a$16$HWU2yLVoO7dT6J6lBtl1kOnwNToSVfqcBM9B8/Rmzwpsg/aO3ABEe", null, null);
        user.getRoles().add(new Role(1L, RoleEnum.ROLE_USER));
        return user;
    }
}
