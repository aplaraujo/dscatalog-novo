package io.github.aplaraujo.security;

import io.github.aplaraujo.entities.Role;
import io.github.aplaraujo.entities.User;
import io.github.aplaraujo.entities.enums.RoleEnum;
import io.github.aplaraujo.repositories.RoleRepository;
import io.github.aplaraujo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found!"));
        return UserDetailsImpl.build(user);
    }

    public String addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (user.getAuthority() != null && !user.getAuthority().isEmpty()) {
            for (String auth : user.getAuthority()) {
                try {
                    // Converte String para RoleEnum
                    RoleEnum roleEnum = RoleEnum.valueOf(auth);

                    Role role = roleRepository.findByAuthority(roleEnum)
                            .orElseGet(() -> {
                                // Se a role não existe, cria uma nova
                                Role newRole = new Role();
                                newRole.setAuthority(roleEnum);
                                return roleRepository.save(newRole);
                            });
                    roles.add(role);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid role: " + auth);
                }
            }
        }
        user.setRoles(roles);
        repository.save(user);
        return "User added successfully!";
    }
}
