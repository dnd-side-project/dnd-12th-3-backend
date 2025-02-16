package com.dnd.backend.user.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dnd.backend.user.exception.UnauthorizedException;
import com.dnd.backend.user.security.CustomUserDetailsService;
import com.dnd.backend.user.security.JwtTokenProvider;
import com.dnd.backend.user.security.UserPrincipal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider tokenProvider;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain)
		throws ServletException, IOException {
		String token = getJwtFromRequest(request);

		if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
			Long userId = tokenProvider.getUserIdFromJWT(token);
			UserDetails userDetails = customUserDetailsService.loadUserById(userId);
			// 만약 반환된 값이 UserPrincipal이 아니라면 (예: "anonymousUser" 등) 예외 발생
			if (!(userDetails instanceof UserPrincipal)) {
				throw new UnauthorizedException("유효한 인증 토큰이 필요합니다.");
			}
			UserPrincipal principal = (UserPrincipal)userDetails;
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				principal, null, principal.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
