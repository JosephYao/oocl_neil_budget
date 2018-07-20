package com.oocl.course;

import java.time.LocalDate;

public class BudgetService {

    private BudgetDao budgetDao;

    public BudgetService(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    public double queryBudget(LocalDate start, LocalDate end) {
        return budgetDao.getAllBudges().stream()
                .mapToDouble(budget -> budget.getOverlappingAmount(new Duration(start, end)))
                .sum();
    }

}

