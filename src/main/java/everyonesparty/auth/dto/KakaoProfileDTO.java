package everyonesparty.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/***
 * > TODO: 추후 KakaoProfileDTO 스팩 정리 필요
 *      > kakao 계정 정보 중에서 우리 서비스에서 필요한 정보가 있으면
 *      > https://developers.kakao.com/console/app/788754/product/login/scope
 *      > 위 링크에서 사용자 동의 항목 수정하여 사용자 동의를 받은 후 우리 서비스에서 수신해서 사용하면 된다.
 *
 *  > 응답 스팩
 *      > https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoProfileDTO {

    @NotBlank
    @Size(min = 10, max = 10)
    private String id;  // 카카오 회원 번호(카카오계정마다 발급되는 고유한 id 값)
                        // -> 사용자 고유번호(다른 계정 시스템이 추가되면 이게 고유하다는 조건이 께질수 있음에 유의!)

    @Valid
    @NotNull
    private Properties properties;

    @Valid  // inner object 에서도 검증하라는 의미
    @NotNull
    private KakaoAccount kakao_account;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KakaoAccount {

        @NotNull
        @Email
        private String email;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Properties {

        @NotBlank
        @Size(max = 1000)
        private String nickname;
    }
}
