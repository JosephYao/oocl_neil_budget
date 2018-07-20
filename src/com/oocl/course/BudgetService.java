package com.oocl.course;

import java.time.LocalDate;
import java.util.List;

public class BudgetService {

    private BudgetDao budgetDao;

    public BudgetService(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    public double queryBudget(LocalDate start, LocalDate end) {
        List<Budget> budgets = budgetDao.getAllBudges();

        Duration duration = new Duration(start, end);
        double total = 0;

        for (Budget budget : budgets) {
            total += budget.getOverlappingAmount(duration);
        }

        return total;
    }

}

