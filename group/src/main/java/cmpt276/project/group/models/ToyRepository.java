package cmpt276.project.group.models; // Add the missing package statement

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ToyRepository extends JpaRepository<Toy, Integer> {
    List<Toy> findByName(String name);
    List<Toy> findByAgeRange(String ageRange);
    List<Toy> findByQuantity(int quantity);
    List<Toy> findByNameAndAgeRangeAndQuantity(String name, String ageRange, int quantity);
    List<Toy> findAll(Sort sort);
}
