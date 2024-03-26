package cmpt276.project.group.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer>{
    List<Users> findByName(String name);
    List<Users> findByUsername(String username);
    List<Users> findByUsernameAndPassword(String username, String password);
    List<Users> findByNameAndUsernameAndPassword(String name, String username, String password);
    boolean existsByUsername(String username);
}
