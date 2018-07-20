package com.oocl.course;

import java.time.LocalDate;
import java.util.List;

public class BudgetService {

    private BudgetDao budgetDao;

    public BudgetService(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    public double queryBudget(LocalDate start, LocalDate end) {
        if (start.isAfter(end))
            return 0;

        return queryTotal(new Duration(start, end));
    }

    private double queryTotal(Duration duration) {
        List<Budget> budgets = budgetDao.getAllBudges();

        double total = 0;
        for (Budget budget : budgets) {
            total += budget.getDailyAmount() * duration.getOverlappingDays(budget.getDuration());
        }

        return total;
    }

}

