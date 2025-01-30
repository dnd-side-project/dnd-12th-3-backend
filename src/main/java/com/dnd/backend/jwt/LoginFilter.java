
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (request.getContentType() == null || !request.getContentType().equals("application/json")) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        ObjectMapper objectMapper =  new ObjectMapper();

        ServletInputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String messageBody = null;
        try {
            messageBody = copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserSignInRequestDto userSignInRequestDto = new UserSignInRequestDto();
        try {
            userSignInRequestDto = objectMapper.readValue(messageBody, UserSignInRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        //클라이언트 요청에서 username, password 추출
        String username = userSignInRequestDto.getUsername();
        String password = userSignInRequestDto.getPassword();

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String email = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String authority = auth.getAuthority();
        Role role = Role.fromString(authority);




        String token = jwtUtil.createJwt(email, role.toString(), 600000L); //10분

        response.addHeader("Authorization", "Bearer " + token);

        // 로그인 이후 리다이렉션할 URL 생성
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/")
                .toUriString();

        response.addHeader("Location", redirectUrl);


        System.out.println("------------------------");
        System.out.println("Login Success");
        System.out.println("------------------------");
    }

    //로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);

    }
}