package ru.kpfu.itis.lobanov.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.model.entity.Role;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.repositories.UserRepository;
import ru.kpfu.itis.lobanov.util.PasswordCryptographer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final PasswordCryptographer passwordCryptographer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        User user = userRepository.findByLogin(login);
        String password = authentication.getCredentials().toString();
        if (!passwordCryptographer.encrypt(password).equals(user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r.getName())));
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
