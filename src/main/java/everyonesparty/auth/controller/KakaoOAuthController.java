package everyonesparty.auth.controller;

import everyonesparty.auth.dto.KakaoJwtTokenDTO;
import everyonesparty.auth.dto.KakaoProfileDTO;
import everyonesparty.auth.jwt.JwtTokenProvider;
import everyonesparty.auth.jwt.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import everyonesparty.auth.dto.KakaoAccessTokenDTO;
import everyonesparty.auth.service.KakaoOAuthService;

import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/login/kakao")
@Slf4j
@RequiredArgsConstructor
public class KakaoOAuthController {

    private final KakaoOAuthService kakaoOAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public KakaoJwtTokenDTO loginByAccessToken(@RequestBody KakaoAccessTokenDTO kakaoAccessTokenDTO){
        KakaoProfileDTO kakaoProfileDTO = kakaoOAuthService.getKakaoProfileDTO(kakaoAccessTokenDTO.getAccessToken());

        return KakaoJwtTokenDTO.builder()
                .kakaoUserId(kakaoProfileDTO.getId())
                .jwtToken(jwtTokenProvider.createToken(kakaoProfileDTO.getId(), new HashSet<UserRole>(Arrays.asList(UserRole.KAKAO_USER))))
                .build();
    }
}
