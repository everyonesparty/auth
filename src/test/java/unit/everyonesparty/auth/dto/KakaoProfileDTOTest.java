package everyonesparty.auth.dto;

import everyonesparty.auth.common.validation.CustomCollectionValidator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class KakaoProfileDTOTest {

    private static Validator validator = new CustomCollectionValidator();

    @Test
    void success_case() {

        //given
        KakaoProfileDTO kakaoProfileDTO = KakaoProfileDTO.builder()
                .id("1234567890")
                .properties(KakaoProfileDTO.Properties.builder().nickname("frank.k").build())
                .kakao_account(KakaoProfileDTO.KakaoAccount.builder().email("gshgsh0831@gamil.com").build())
                .build();

        // when
        Errors errors = new BindException(kakaoProfileDTO, "kakaoProfileDTO");
        validator.validate(kakaoProfileDTO,errors);

        // then
        assertTrue(!errors.hasErrors());
    }
}