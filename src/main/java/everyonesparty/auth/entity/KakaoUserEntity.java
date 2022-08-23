package everyonesparty.auth.entity;

import everyonesparty.auth.dto.KakaoUserDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "kakao_user_table")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KakaoUserEntity {

    @Id
    private String kakaoId;
    private String email;

    //TODO: 추후 사업자 등록이 되고 카카오 계정에서 추가적인 정보가 필요하게 되면 추가 예정

    public KakaoUserDTO toDTO(){
        return KakaoUserDTO.builder()
                .kakaoId(kakaoId)
                .email(email)
                .build();
    }
}
