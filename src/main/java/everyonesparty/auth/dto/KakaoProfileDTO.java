package everyonesparty.auth.dto;

import lombok.Data;

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
public class KakaoProfileDTO {
    private String id;  // 카카오 회원 번호(카카오계정마다 발급되는 고유한 id 값)
                        // -> 사용자 고유번호(다른 계정 시스템이 추가되면 이게 고유하다는 조건이 께질수 있음에 유의!)

    private Properties properties;
    private KakaoAccount kakao_account;

    @Data
    public static class KakaoAccount {
        private String email;
    }

    @Data
    public static class Properties {
        private String nickname;
    }
}
