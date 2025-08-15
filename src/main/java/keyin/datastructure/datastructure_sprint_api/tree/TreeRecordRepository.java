package keyin.datastructure.datastructure_sprint_api.tree;

import keyin.datastructure.datastructure_sprint_api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TreeRecordRepository extends JpaRepository<TreeRecord, Long> {

    // This method will find all tree records created by a specific user
    List<TreeRecord> findAllByUser(User user);

}