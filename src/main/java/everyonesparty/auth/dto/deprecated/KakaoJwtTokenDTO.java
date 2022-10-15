package everyonesparty.auth.dto.deprecated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Deprecated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoJwtTokenDTO {
    private String jwtToken;
    private String kakaoId;
}
