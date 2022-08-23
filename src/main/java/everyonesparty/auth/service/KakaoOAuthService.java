package everyonesparty.auth.service;

import everyonesparty.auth.dto.KakaoProfileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoOAuthService {

    @Value("${kakao.api.base-url}")
    private String baseUrl;

    @Value("${kakao.oauth.client-id}")
    private String kakaoClientId;

    @Value("${kakao.oauth.redirect-uri}")
    private String kakaoRedirectUri;

    private final WebClient webClient;

    public KakaoProfileDTO getKakaoProfileDTO(String accessToken){
        return webClient.mutate().baseUrl(baseUrl).build()
                .post()
                .uri(uriBuilder -> uriBuilder.path("/v2/user/me").build())
                .header("Authorization","Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoProfileDTO.class)
                .block();
    }
}
