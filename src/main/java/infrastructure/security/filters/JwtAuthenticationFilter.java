package infrastructure.security.filters;

import infrastructure.security.JwtUtils;
import infrastructure.security.UserPrincipal;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import services.UserService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtils.getTokenFromRequest(request);
        if (StringUtils.hasText(token) && jwtUtils.validateToken(token)) {
            String email = jwtUtils.getEmailFromToken(token);
            UserPrincipal userPrincipal = userService.loadUserPrincipalByEmail(email);
            userPrincipal.setToken(token);

            Set<GrantedAuthority> authorities = new HashSet<>();
            userPrincipal.getAuthorities()
                .forEach(p -> authorities.add(new SimpleGrantedAuthority((String) p)));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
