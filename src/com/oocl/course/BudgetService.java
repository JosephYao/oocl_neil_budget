package com.oocl.course;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class BudgetService {

    private BudgetDao budgetDao;

    public BudgetService(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    private boolean isSameMonth(LocalDate start, LocalDate end) {
        return YearMonth.from(start).equals(YearMonth.from(end));
    }

    public double queryBudget(LocalDate start, LocalDate end) {
        if (start.isAfter(end))
            return 0;

        List<Budget> budgets = budgetDao.getAllBudges();

        if (isSameMonth(start, end)) {
            for (Budget budget : budgets) {
                if (isSameMonth(budget.getDate(), start)) {
                    return (end.getDayOfMonth() - start.getDayOfMonth() + 1.0) / start.lengthOfMonth() * budget.getAmount();
                }
            }
            return 0;
        }

        double total = 0;
        for (Budget budget : budgets) {
            if (isSameMonth(budget.getDate(), start)) {
                total += (start.lengthOfMonth() - start.getDayOfMonth() + 1.0) / start.lengthOfMonth() * budget.getAmount();
            }
            if (isSameMonth(budget.getDate(), end)) {
                total += (end.getDayOfMonth() + 0.0) / end.lengthOfMonth() * budget.amount;
            }

        }
        for (LocalDate date = start.plusMonths(1); !isSameMonth(date, end); date = date.plusMonths(1)) {
            for (Budget budget1 : budgets) {
                if (isSameMonth(budget1.getDate(), date))
                    total += budget1.getAmount();
            }
        }

        return total;
    }
}

