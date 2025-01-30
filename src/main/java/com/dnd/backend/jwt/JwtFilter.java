@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");


        //Authorization 헤더 검사
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("JWTFilter token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 매소드 종료 (필수!)
            return;
        }


        //Bearer 부분 제거 후 순수 토큰 획득 -> 토큰 시간 검증
        String token = authorization.split(" ")[1];
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);

            return;
        }


        //토큰에서 username, role 획득
        String email = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);


        //Member 엔티티 생성하여 값 세팅
        User user = User.builder()
                .email(email)
                .password("tempPassword")
                .role(Role.valueOf(role))
                .build();


        //UserDetails에 회원정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}