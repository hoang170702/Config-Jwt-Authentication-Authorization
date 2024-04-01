    package com.hoang.springapijwt.configuration;

    import com.hoang.springapijwt.service.user.CustomUserDetails;
    import com.hoang.springapijwt.service.user.CustomUserDetailsService;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
    import org.springframework.stereotype.Component;
    import org.springframework.util.StringUtils;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;

    @Slf4j
    @Component
    public class JwtAuthenticationFilter extends OncePerRequestFilter {

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        private String getJwtFromRequest(HttpServletRequest request){
            String bearerToken = request.getHeader("Authorization");
            if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
                return bearerToken.substring(7);
            }
            return null;
        }
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {
            try {
                String jwt = getJwtFromRequest(request);
                if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
                    int userId = jwtTokenProvider.getUserIdFromJWT(jwt);
                    CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUserId(userId);
                    if(userDetails != null){
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }catch (Exception ex) {
                log.error("failed on set user authentication", ex);
            }finally {
                filterChain.doFilter(request,response);
            }
        }
    }
