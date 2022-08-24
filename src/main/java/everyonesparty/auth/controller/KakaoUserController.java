package everyonesparty.auth.controller;

import everyonesparty.auth.common.response.Response;
import everyonesparty.auth.common.response.ResponseUtils;
import everyonesparty.auth.dto.KakaoJwtTokenDTO;
import everyonesparty.auth.dto.KakaoProfileDTO;
import everyonesparty.auth.dto.KakaoUserDTO;
import everyonesparty.auth.jwt.JwtTokenProvider;
import everyonesparty.auth.jwt.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import everyonesparty.auth.dto.KakaoAccessTokenDTO;
import everyonesparty.auth.service.KakaoUserService;

import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/login/kakao")
@Slf4j
@RequiredArgsConstructor
public class KakaoUserController {

    private final KakaoUserService kakaoUserService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping
    public ResponseEntity<?> loginByAccessToken(@RequestBody KakaoAccessTokenDTO kakaoAccessTokenDTO){
        KakaoProfileDTO kakaoProfileDTO = kakaoUserService.getKakaoProfileDTO(kakaoAccessTokenDTO.getAccessToken());

        kakaoUserService.saveKakaoUser(kakaoProfileDTO);

        return ResponseUtils.out(KakaoJwtTokenDTO.builder()
                .kakaoUserId(kakaoProfileDTO.getId())
                .jwtToken(jwtTokenProvider.createToken(kakaoProfileDTO.getId(), new HashSet<UserRole>(Arrays.asList(UserRole.KAKAO_USER))))
                .build());
    }

    @GetMapping("/{kakaoId}")
    public ResponseEntity<?> getKakaoInfoByKakaoId(@PathVariable("kakaoId") String kakaoId){
        return ResponseUtils.out(kakaoUserService.findKakaoUserByKakaoId(kakaoId));
    }
}
