package repo;
import model.entity.RecommendationMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationMsgRepository extends JpaRepository<RecommendationMsg, Long> {

}