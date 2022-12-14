package everyonesparty.auth.repository;

import everyonesparty.auth.entity.KakaoUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoUserRepository extends JpaRepository<KakaoUserEntity, String> {
    Optional<KakaoUserEntity> findByKakaoId(String kakaoId);
}
