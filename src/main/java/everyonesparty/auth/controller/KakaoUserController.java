package everyonesparty.auth.controller;

import everyonesparty.auth.common.response.ResponseUtils;
import everyonesparty.auth.dto.KakaoJwtTokenDTO;
import everyonesparty.auth.jwt.JwtTokenProvider;
import everyonesparty.auth.jwt.UserRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(tags = {"* 인증 API"})
@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/login/kakao")
public class KakaoUserController {

    private final KakaoUserService kakaoUserService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "카카오톡 로그인", notes = "${노션 api 링크}")
    @PostMapping
    public ResponseEntity<Mono<?>> loginByAccessToken(
            @ApiParam(value = "로그인을 위한 access token 정보", required = true) @RequestBody KakaoAccessTokenDTO kakaoAccessTokenDTO){
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

    /***
     * > TODO: jwt token 이 요구되는 api 이지만 여기서 검증하지 않고 앞단의 api-gateway 에서 검증하도록 할 것임
     *      > 이 TODO 는 api-gateway 작업이 끝나면 지우도록 함
     * @param kakaoId
     * @return
     */
    @ApiOperation(value = "카카오톡 사용자 정보 조회", notes = "${노션 api 링크}")
    @GetMapping("/{kakaoId}")
    public ResponseEntity<?> getKakaoUserInfoByKakaoId(
            @PathVariable("kakaoId") @NotBlank @Size(min = 10, max = 10) String kakaoId){
        return ResponseUtils.out(kakaoUserService.findKakaoUserByKakaoId(kakaoId));
    }
}
