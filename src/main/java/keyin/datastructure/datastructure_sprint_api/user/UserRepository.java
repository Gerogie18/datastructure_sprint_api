package keyin.datastructure.datastructure_sprint_api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA automatically implements this method based on its name
    Optional<User> findByName(String name);

}
