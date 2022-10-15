package everyonesparty.auth.controller.deprecated;

import everyonesparty.auth.service.deprecated.KakaoOAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import everyonesparty.auth.dto.deprecated.KakaoAccessTokenDTO;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Deprecated
public class OAuthController {

    private final KakaoOAuthService kakaoOAuthService;

    @GetMapping("/kakao/redirect")
    public Mono<KakaoAccessTokenDTO> kakaoCallback(@RequestParam String code) {
        log.info(code);
        return kakaoOAuthService.getKakaoAccessToken(code);
    }

}
