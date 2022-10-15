package everyonesparty.auth.controller;

import everyonesparty.auth.dto.KakaoUserDTO;
import everyonesparty.auth.service.JwtService;
import everyonesparty.auth.service.KakaoUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

@WebMvcTest(KakaoUserController.class)
@AutoConfigureWebTestClient
class KakaoUserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private KakaoUserService kakaoUserService;

    @MockBean
    private JwtService jwtService;

    static String url = "/api/v1/login/kakao";


    @Test
    public void getKakaoUserInfoByKakaoId(){
        // given
        String kakaoId = "01234567891";
        KakaoUserDTO kakaoUserDTO = KakaoUserDTO.builder()
                .kakaoId(kakaoId)
                .email("gshgsh0831@gmail.com")
                .build();

        when(kakaoUserService.findKakaoUserByKakaoId(kakaoId))
                .thenReturn(kakaoUserDTO);

        // when & then
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(url).path("/" + kakaoId).build())
                .exchange()
                .expectStatus().isBadRequest();
    }
}