package cmpt276.project.group.models; // Add the missing package statement

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClothingRepository extends JpaRepository<Clothing, Integer> {
    List<Clothing> findByType(String type);
    List<Clothing> findByGender(String gender);
    List<Clothing> findByAgeRange(String ageRange);
    List<Clothing> findByQuantity(int quantity);
    List<Clothing> findByTypeAndGenderAndAgeRangeAndQuantity(String type,String gender,String ageRange, int quantity);
    List<Clothing> findAll(Sort sort);

}