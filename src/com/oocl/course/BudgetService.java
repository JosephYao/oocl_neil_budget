package com.oocl.course;

import java.time.LocalDate;
import java.util.List;

public class BudgetService {

    private BudgetDao budgetDao;
    
    public BudgetService( BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }
    
    private boolean isSameMonth(LocalDate a, LocalDate b) {
        return a.getMonth() ==  b.getMonth() && a.getYear() == b.getYear();
    }

    public double queryBudget(LocalDate start, LocalDate end) {

        List<Budget> budgetList = budgetDao.getAllBudges();
        if (start.isAfter(end))
            return 0;
        double total = 0;
        if (isSameMonth(start,end)) {
            for (Budget budget : budgetList) {
                if (isSameMonth(budget.getDate(), start)) {
                    total += (end.getDayOfMonth() - start.getDayOfMonth() + 1.0) / start.lengthOfMonth() * budget.getAmount();
                }
            }
        }else{
            for(Budget budget : budgetList){
                if(isSameMonth(budget.getDate(),start)){
                    total+=(start.lengthOfMonth() - start.getDayOfMonth() + 1.0) / start.lengthOfMonth() * budget.getAmount();
                }
                if(isSameMonth(budget.getDate(),end)){
                    total+=(end.getDayOfMonth()+0.0)/end.lengthOfMonth()*budget.amount;
                }

            }
            for(LocalDate date=start.plusMonths(1);!isSameMonth(date,end);date=date.plusMonths(1)){
                for(Budget budget1:budgetList){
                    if(isSameMonth(budget1.getDate(),date))
                        total+=budget1.getAmount();
                }
            }
        }

        return total;
    }
}

