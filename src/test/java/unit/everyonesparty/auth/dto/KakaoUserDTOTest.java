package everyonesparty.auth.dto;

import everyonesparty.auth.common.validation.CustomCollectionValidator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class KakaoUserDTOTest {
    private static Validator validator = new CustomCollectionValidator();

    @Test
    void success_case() {

        //given
        KakaoUserDTO kakaoUserDTO = KakaoUserDTO.builder()
                .kakaoId("1234567890")
                .email("gshgsh0831@gmail.com")
                .build();

        // when
        Errors errors = new BindException(kakaoUserDTO, "kakaoUserDTO");
        validator.validate(kakaoUserDTO,errors);

        // then
        assertTrue(!errors.hasErrors());
    }

    @Test
    void fail_case_email_format() {

        //given
        KakaoUserDTO kakaoUserDTO = KakaoUserDTO.builder()
                .kakaoId("1234567890")
                .email("gshgsh0831gmailcom")
                .build();

        // when
        Errors errors = new BindException(kakaoUserDTO, "kakaoUserDTO");
        validator.validate(kakaoUserDTO,errors);

        // then
        assertTrue(errors.hasErrors());
    }

    @Test
    void fail_case_email_empty() {

        //given
        KakaoUserDTO kakaoUserDTO = KakaoUserDTO.builder()
                .kakaoId("1234567890")
                .email("")
                .build();

        // when
        Errors errors = new BindException(kakaoUserDTO, "kakaoUserDTO");
        validator.validate(kakaoUserDTO,errors);

        // then
        assertTrue(errors.hasErrors());
    }

    @Test
    void fail_case_email_null() {

        //given
        KakaoUserDTO kakaoUserDTO = KakaoUserDTO.builder()
                .kakaoId("1234567890")
                .build();

        // when
        Errors errors = new BindException(kakaoUserDTO, "kakaoUserDTO");
        validator.validate(kakaoUserDTO,errors);

        // then
        assertTrue(errors.hasErrors());
    }
}