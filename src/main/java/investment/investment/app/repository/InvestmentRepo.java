package investment.investment.app.repository;

import investment.investment.app.Entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentRepo extends JpaRepository<Investment , Long>{


}
