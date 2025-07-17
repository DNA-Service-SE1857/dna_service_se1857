package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.TestResult;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, String> {


    List<TestResult> findByOrdersId(String orderId);

    List<TestResult> findByUserId(String userId);

    List<TestResult> findBySamplesId(String samplesId);
}
