package investment.investment.app.controller;

import investment.investment.app.Entity.Investment;
import investment.investment.app.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/investment")
public class InvestmentController {

    @Autowired
   InvestmentService  investmentService;


    @GetMapping("/home")
    public String viewHomePage(Model model) {

        List<Investment> investments = investmentService.getAllInvestment();
        model.addAttribute("allInvestments", investments);

        BigDecimal totalInvested = investmentService.totalInvestmentAmount();
        BigDecimal availableFunds = investmentService.availableFunds();


        model.addAttribute("totalInvested", totalInvested);
        model.addAttribute("availableFunds", availableFunds);
        return "home";



    }
   @GetMapping("/create")
   public String showInv(Model model){
        model.addAttribute("investment", new Investment());
        return "create";

   }
   @PostMapping("/create")
    public String createInvestment(@ModelAttribute Investment investment){
        investmentService.CreateInvestment(investment);
        return "redirect:/investment/home" ;
    }

//    @PostMapping("/create")
//    @ResponseBody
//    public ResponseEntity<String> createInvestment(@ModelAttribute Investment investment) {
//        investmentService.CreateInvestment(investment);
//        return ResponseEntity.ok("{\"message\": \"Investment created successfully!\"}");
//    }
    @GetMapping("/details/{id}")
    public String getInvestmentDetailsById(@PathVariable Long id, Model model)
    {
       Investment investment = investmentService.getInvestmentDetailsById(id);
       model.addAttribute("investment", investment);
       return "details";
    }
    @PostMapping("/update/{id}")
    public String updateInvestment(@PathVariable Long id, @RequestParam String name, @RequestParam BigDecimal amount) {
        investmentService.updateInvestment(id, name, amount);
        return "redirect:/investment/details/" + id;
    }


    @PostMapping("/addFunds")
    public String addFunds(@RequestParam BigDecimal amount) {
        investmentService.addFunds(amount);
        return "redirect:/investment/home";
    }


    @GetMapping("/sort")
    public String sortInvestments(@RequestParam String sortBy, @RequestParam String order, Model model) {
        System.out.println("Sorting by: " + sortBy + ", Order: " + order);  // Debugging output
        List<Investment> sortedInvestments = investmentService.getSortedInvestments(sortBy, order);
        model.addAttribute("allInvestments", sortedInvestments);
        return "home";
    }
    @PostMapping("/delete/{id}")
    public String deleteInvestment(@PathVariable Long id) {
        investmentService.deleteInvestment(id);
        return "redirect:/investment/home";
    }

}
