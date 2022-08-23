package everyonesparty.auth.frontMock_deprecated.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import everyonesparty.auth.dto.KakaoAccessTokenDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService_Deprecated {

    private final WebClient webClient;

    @Value("${kakao.oauth.base-url}")
    private String baseUrl;

    @Value("${kakao.oauth.client-id}")
    private String kakaoClientId;

    @Value("${kakao.oauth.redirect-uri}")
    private String kakaoRedirectUri;

    public Mono<KakaoAccessTokenDTO> getKakaoAccessToken(String code){
        return webClient.mutate().baseUrl(baseUrl).build()
                .post()
                .uri(uriBuilder -> uriBuilder.path("/oauth/token").build())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type","authorization_code")
                        .with("client_id",kakaoClientId)
                        .with("redirect_uri",kakaoRedirectUri)
                        .with("code",code))
                .retrieve()
                .bodyToMono(KakaoAccessTokenDTO.class)
                .onErrorMap(e -> {
                    log.error(e.getMessage());
                    return e;
                });
    }

}
