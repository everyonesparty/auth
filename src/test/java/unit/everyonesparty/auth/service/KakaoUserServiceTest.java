package everyonesparty.auth.service;

import everyonesparty.auth.common.exception.LogicalRuntimeException;
import everyonesparty.auth.dto.KakaoUserDTO;
import everyonesparty.auth.entity.KakaoUserEntity;
import everyonesparty.auth.repository.KakaoUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = KakaoUserService.class)
class KakaoUserServiceTest {

    @Autowired
    private KakaoUserService kakaoUserService;

    @MockBean
    private KakaoUserRepository kakaoUserRepository;

    @MockBean
    private WebClient webClient;

    @MockBean
    private Validator validator;

    @Test
    void findKakaoUserByKakaoId() {
        // given
        String kakaoId = "0123456789";
        String kakaoEmail = "ksh0831@gmail.com";

        KakaoUserEntity givenKakaoUserEntity = KakaoUserEntity.builder()
                .kakaoId(kakaoId)
                .email(kakaoEmail)
                .build();

        when(kakaoUserRepository.findByKakaoId(kakaoId)).thenReturn(Optional.of(givenKakaoUserEntity));

        // when
        KakaoUserDTO kakaoUserDTO = kakaoUserService.findKakaoUserByKakaoId(kakaoId);

        // then
        assertEquals(kakaoUserDTO.getKakaoId(), kakaoId);
        assertEquals(kakaoUserDTO.getEmail(), kakaoEmail);
    }

    @Test
    void findKakaoUserByKakaoId_throw() {
        // given
        String kakaoId = "0123456789";

        when(kakaoUserRepository.findByKakaoId(kakaoId)).thenReturn(Optional.ofNullable(null));

        // when & then
        assertThrows(LogicalRuntimeException.class, () -> {
            kakaoUserService.findKakaoUserByKakaoId(kakaoId);
        });
    }
}