package everyonesparty.auth.controller;

import everyonesparty.auth.common.response.ResponseUtils;
import everyonesparty.auth.dto.KakaoJwtTokenDTO;
import everyonesparty.auth.dto.KakaoUserDTO;
import everyonesparty.auth.jwt.JwtTokenProvider;
import everyonesparty.auth.jwt.UserRole;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import everyonesparty.auth.dto.KakaoAccessTokenDTO;
import everyonesparty.auth.service.KakaoUserService;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.HashSet;

@Api(tags = {"* 인증 API"})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/login/kakao")
public class KakaoUserController {

    private final KakaoUserService kakaoUserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Deprecated
    @ApiOperation(value = "카카오 로그인(미 사용)", notes = "https://keen-derby-c16.notion.site/2b2c57f1826f451d854b8c3dc2979309")
    @PostMapping("/test")
    public ResponseEntity<Mono<?>> loginByAccessToken(
            @ApiParam(value = "로그인을 위한 access token 정보", required = true) @Valid @RequestBody KakaoAccessTokenDTO kakaoAccessTokenDTO){
        return ResponseUtils.out(
                kakaoUserService.getKakaoProfileDTO(kakaoAccessTokenDTO.getAccessToken())
                        .map(kakaoProfileDTO -> KakaoUserDTO.builder()
                                .kakaoId(kakaoProfileDTO.getId())
                                .email(kakaoProfileDTO.getKakao_account().getEmail()).build())
                        .map(kakaoUserDTO ->  {
//                            kakaoUserService.saveKakaoUser(kakaoUserDTO);
                            return KakaoJwtTokenDTO.builder()
                                    .kakaoId(kakaoUserDTO.getKakaoId())
                                    .jwtToken(jwtTokenProvider.createToken(kakaoUserDTO.getKakaoId(), new HashSet<UserRole>(Arrays.asList(UserRole.KAKAO_USER))))
                                    .build();
                        }));
    }

    @ApiOperation(value = "카카오 정보 등록", notes = "https://keen-derby-c16.notion.site/cea698a991464699a98dbcd7ed7dcfeb")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "카카오 로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping
    public ResponseEntity<?> registerKakaoUserInfo(
            @ApiParam(value = "카카오 측에서 받은 정보", required = true) @Valid @RequestBody KakaoUserDTO kakaoUserDTO){
        kakaoUserService.saveKakaoUser(kakaoUserDTO);
        return ResponseUtils.ok();
    }

    /***
     *
     * @param kakaoId
     * @return
     */
    @ApiOperation(value = "카카오 사용자 정보 조회", notes = "https://keen-derby-c16.notion.site/ff991fc883b2497b9f50a0cd8d9f48d1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "카카오 로그인 성공 후 jwt token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/{kakaoId}")
    public ResponseEntity<?> getKakaoUserInfoByKakaoId(
            @PathVariable("kakaoId") @NotBlank @Size(min = 10, max = 10) String kakaoId){
        return ResponseUtils.out(kakaoUserService.findKakaoUserByKakaoId(kakaoId));
    }
}
