package investment.investment.app.service;

import investment.investment.app.Entity.Investment;
import investment.investment.app.repository.InvestmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class InvestmentServiceImpl implements InvestmentService{
    @Autowired
    InvestmentRepo repo;
    private BigDecimal totalFunds = new BigDecimal("10000000");

    @Override
    public List<Investment> getAllInvestment() {
        return repo.findAll();
    }

    @Override
    public Investment CreateInvestment(Investment investment) {
        BigDecimal availableFunds = availableFunds();

        if (investment.getAmount().compareTo(availableFunds) <= 0) {
            return repo.save(investment);
        }
        throw new RuntimeException("Insufficient funds");
    }

    @Override

    public Investment getInvestmentDetailsById(Long id) {
        Optional<Investment> investment = repo.findById(id);
        if (investment.isPresent()) {
            return investment.get();
        } else {
            throw new RuntimeException("Investment not found");
        }
    }


    @Override
    public void addFunds(BigDecimal Amount) {

        totalFunds = totalFunds.add(Amount);
    }

    @Override
    public void updateInvestment(Long id, String name, BigDecimal amount) {
        Investment investment = getInvestmentDetailsById(id);
        investment.setName(name);
        investment.setAmount(amount);
        repo.save(investment);
    }



    @Override
    public BigDecimal totalInvestmentAmount() {
        List<Investment> investments = repo.findAll();
        BigDecimal total = BigDecimal.ZERO;
        for (Investment investment : investments) {
            total = total.add(investment.getAmount());
        }
        return total;
    }

    @Override
    public BigDecimal availableFunds() {
        return totalFunds.subtract(totalInvestmentAmount());
    }

    @Override
    public List<Investment> getSortedInvestments(String sortBy, String order) {
        Sort sort = Sort.by(order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        return repo.findAll(sort);
    }


    @Override
    public void deleteInvestment(Long id) {
      repo.deleteById(id);
    }
}
