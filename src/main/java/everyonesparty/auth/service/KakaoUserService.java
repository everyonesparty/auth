package everyonesparty.auth.service;

import everyonesparty.auth.common.exception.LogicalRuntimeException;
import everyonesparty.auth.common.exception.error.CommonError;
import everyonesparty.auth.dto.KakaoProfileDTO;
import everyonesparty.auth.dto.KakaoUserDTO;
import everyonesparty.auth.entity.KakaoUserEntity;
import everyonesparty.auth.repository.KakaoUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class KakaoUserService {

    @Value("${kakao.api.base-url}")
    private String baseUrl;

    @Value("${kakao.oauth.client-id}")
    private String kakaoClientId;

    @Value("${kakao.oauth.redirect-uri}")
    private String kakaoRedirectUri;

    private final WebClient webClient;
    private final KakaoUserRepository kakaoUserRepository;
    private final Validator validator;

    public Mono<KakaoProfileDTO> getKakaoProfileDTO(String accessToken){
        return webClient.mutate().baseUrl(baseUrl).build()
                .post()
                .uri(uriBuilder -> uriBuilder.path("/v2/user/me").build())
                .header("Authorization","Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoProfileDTO.class)
                .doOnError(exception -> {
                    log.info("kakao 통신 오류] 오류 메시지: {}", exception.getMessage());
                    throw new LogicalRuntimeException(CommonError.INTERNAL_SERVER_ERROR);
                })
                .doOnNext(this::validateKakaoProfileDTO);
    }

    public void saveKakaoUser(KakaoProfileDTO kakaoProfileDTO){
        kakaoUserRepository.save(
                KakaoUserEntity.builder()
                        .kakaoId(kakaoProfileDTO.getId())
                        .email(kakaoProfileDTO.getKakao_account().getEmail())
                        .build()
        );
    }


    public KakaoUserDTO findKakaoUserByKakaoId(String kakaoId){
        return kakaoUserRepository.findByKakaoId(kakaoId)
                .map(kakaoUserEntity -> kakaoUserEntity.toDTO())
                .orElseThrow(() -> new LogicalRuntimeException(CommonError.INTERNAL_SERVER_ERROR));
    }



    private void validateKakaoProfileDTO(KakaoProfileDTO kakaoProfileDTO){
        Errors errors = new BindException(kakaoProfileDTO, "dummystr");
        validator.validate(kakaoProfileDTO, errors);
        if(errors.hasErrors()){
            log.error("[kakao 통신 오류] KakaoProfileDTO 의 스팩이 맞지 않습니다. KakaoProfileDTO: {}", kakaoProfileDTO.toString());
            throw new LogicalRuntimeException(CommonError.INTERNAL_SERVER_ERROR);
        }
    }
}
