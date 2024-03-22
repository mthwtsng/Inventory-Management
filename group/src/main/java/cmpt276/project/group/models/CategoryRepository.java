package cmpt276.project.group.models; // Add the missing package statement

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByName(String name);
    List<Category> findByAgeRange(String ageRange);
    List<Category> findByQuantity(int quantity);
    List<Category> findByNameAndAgeRangeAndQuantity(String name, String ageRange, int quantity);
}