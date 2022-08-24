package everyonesparty.auth.controller;

import everyonesparty.auth.common.response.ResponseUtils;
import everyonesparty.auth.dto.KakaoJwtTokenDTO;
import everyonesparty.auth.jwt.JwtTokenProvider;
import everyonesparty.auth.jwt.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import everyonesparty.auth.dto.KakaoAccessTokenDTO;
import everyonesparty.auth.service.KakaoUserService;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.HashSet;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/login/kakao")
public class KakaoUserController {

    private final KakaoUserService kakaoUserService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<Mono<?>> loginByAccessToken(@RequestBody KakaoAccessTokenDTO kakaoAccessTokenDTO){
        return ResponseUtils.out(
                kakaoUserService.getKakaoProfileDTO(kakaoAccessTokenDTO.getAccessToken())
                        .map(kakaoProfileDTO ->  {
                            kakaoUserService.saveKakaoUser(kakaoProfileDTO);
                            return KakaoJwtTokenDTO.builder()
                                    .kakaoUserId(kakaoProfileDTO.getId())
                                    .jwtToken(jwtTokenProvider.createToken(kakaoProfileDTO.getId(), new HashSet<UserRole>(Arrays.asList(UserRole.KAKAO_USER))))
                                    .build();
                        }));
    }

    @GetMapping("/{kakaoId}")
    public ResponseEntity<?> getKakaoUserInfoByKakaoId(
            @PathVariable("kakaoId") @NotBlank @Size(min = 10, max = 10) String kakaoId){
        return ResponseUtils.out(kakaoUserService.findKakaoUserByKakaoId(kakaoId));
    }
}
