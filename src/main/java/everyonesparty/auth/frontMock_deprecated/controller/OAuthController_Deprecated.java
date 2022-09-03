package everyonesparty.auth.frontMock_deprecated.controller;

import everyonesparty.auth.frontMock_deprecated.service.OAuthService_Deprecated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import everyonesparty.auth.dto.KakaoAccessTokenDTO;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController_Deprecated {

//    private final OAuthService_Deprecated oAuthService;
//
//    @GetMapping("/kakao/redirect")
//    public Mono<KakaoAccessTokenDTO> kakaoCallback(@RequestParam String code) {
//        log.info(code);
//        return oAuthService.getKakaoAccessToken(code);
//    }

}
