package investment.investment.app.service;

import investment.investment.app.Entity.Investment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InvestmentService {
     List<Investment> getAllInvestment();
     Investment CreateInvestment(Investment investment);
    public Investment getInvestmentDetailsById(Long id);
     void addFunds( BigDecimal Amount);
     void updateInvestment(Long id, String name, BigDecimal amount);
     BigDecimal totalInvestmentAmount();
     BigDecimal availableFunds();

    List<Investment> getSortedInvestments(String sortBy, String order);
    public void deleteInvestment(Long id);
}
