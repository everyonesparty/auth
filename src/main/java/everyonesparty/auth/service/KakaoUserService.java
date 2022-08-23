package everyonesparty.auth.service;

import everyonesparty.auth.dto.KakaoProfileDTO;
import everyonesparty.auth.dto.KakaoUserDTO;
import everyonesparty.auth.entity.KakaoUserEntity;
import everyonesparty.auth.repository.KakaoUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoUserService {

    @Value("${kakao.api.base-url}")
    private String baseUrl;

    @Value("${kakao.oauth.client-id}")
    private String kakaoClientId;

    @Value("${kakao.oauth.redirect-uri}")
    private String kakaoRedirectUri;

    private final WebClient webClient;
    private final KakaoUserRepository kakaoUserRepository;

    public KakaoProfileDTO getKakaoProfileDTO(String accessToken){
        return webClient.mutate().baseUrl(baseUrl).build()
                .post()
                .uri(uriBuilder -> uriBuilder.path("/v2/user/me").build())
                .header("Authorization","Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoProfileDTO.class)
                .block();
    }

    public void saveKakaoUser(KakaoProfileDTO kakaoProfileDTO){
        kakaoUserRepository.save(KakaoUserEntity.builder()
                .kakaoId(kakaoProfileDTO.getId())
                .email(kakaoProfileDTO.getKakao_account().getEmail())
                .build());
    }

    public KakaoUserDTO findKakaoUserByKakaoId(String kakaoId){
        KakaoUserEntity kakaoUserEntity = kakaoUserRepository.findByKakaoId(kakaoId);
        return kakaoUserEntity.toDTO();
    }

}
