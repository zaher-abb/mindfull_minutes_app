package th.project.enterprise.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import th.project.enterprise.Entity.Message;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long> {
}
