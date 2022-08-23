package everyonesparty.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoJwtTokenDTO {
    private String jwtToken;
    private String kakaoUserId;
}
