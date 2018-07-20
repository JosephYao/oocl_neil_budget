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

        if (duration.isSameMonth()) {
            for (Budget budget : budgets) {
                if (new Duration(budget.getDate(), duration.getStart()).isSameMonth()) {
                    return budget.getDailyAmount() * duration.getOverlappingDays(budget.getDuration());
                }
            }
            return 0;
        }

        double total = 0;
        for (Budget budget : budgets) {
            if (new Duration(budget.getDate(), duration.getStart()).isSameMonth()) {
                total += budget.getDailyAmount() * duration.getOverlappingDays(budget.getDuration());
            }
            if (new Duration(budget.getDate(), duration.getEnd()).isSameMonth()) {
                total += budget.getDailyAmount() * duration.getOverlappingDays(budget.getDuration());
            }

            for (LocalDate date = duration.getStart().plusMonths(1); !new Duration(date, duration.getEnd()).isSameMonth(); date = date.plusMonths(1)) {
                if (new Duration(budget.getDate(), date).isSameMonth())
                    total += budget.getDailyAmount() * duration.getOverlappingDays(budget.getDuration());
            }
        }

        return total;
    }

}

