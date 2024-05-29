package co.jammingRules.Servizi._FileUpload.repositories;

import co.jammingRules.Servizi._FileUpload.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
