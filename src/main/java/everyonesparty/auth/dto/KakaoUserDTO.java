package everyonesparty.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoUserDTO {

    @NotBlank
    private String kakaoId;

    @NotBlank   // @NotBlank 는 null 과 "" 과 " " 모두 허용하지 않습니다.
    @Email
    private String email;
}
