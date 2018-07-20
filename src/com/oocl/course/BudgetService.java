package com.oocl.course;

import java.time.LocalDate;
import java.time.Period;
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
                    return budget.getDailyAmount() * (Period.between(start, end).getDays() + 1);
                }
            }
            return 0;
        }

        double total = 0;
        for (Budget budget : budgets) {
            if (isSameMonth(budget.getDate(), start)) {
                total += budget.getDailyAmount() * (Period.between(start, budget.getEnd()).getDays() + 1);
            }
            if (isSameMonth(budget.getDate(), end)) {
                total += budget.getDailyAmount() * (Period.between(budget.getStart(), end).getDays() + 1);
            }

            for (LocalDate date = start.plusMonths(1); !isSameMonth(date, end); date = date.plusMonths(1)) {
                if (isSameMonth(budget.getDate(), date))
                    total += budget.getDailyAmount() * (Period.between(budget.getStart(), budget.getEnd()).getDays() + 1);
            }
        }

        return total;
    }

}

