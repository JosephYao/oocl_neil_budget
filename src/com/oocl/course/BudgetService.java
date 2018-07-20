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

        return queryTotal(new Duration(start, end));
    }

    private double queryTotal(Duration duration) {
        List<Budget> budgets = budgetDao.getAllBudges();

        if (isSameMonth(duration.getStart(), duration.getEnd())) {
            for (Budget budget : budgets) {
                if (isSameMonth(budget.getDate(), duration.getStart())) {
                    return budget.getDailyAmount() * (Period.between(duration.getStart(), duration.getEnd()).getDays() + 1);
                }
            }
            return 0;
        }

        double total = 0;
        for (Budget budget : budgets) {
            if (isSameMonth(budget.getDate(), duration.getStart())) {
                total += budget.getDailyAmount() * (Period.between(duration.getStart(), budget.getEnd()).getDays() + 1);
            }
            if (isSameMonth(budget.getDate(), duration.getEnd())) {
                total += budget.getDailyAmount() * (Period.between(budget.getStart(), duration.getEnd()).getDays() + 1);
            }

            for (LocalDate date = duration.getStart().plusMonths(1); !isSameMonth(date, duration.getEnd()); date = date.plusMonths(1)) {
                if (isSameMonth(budget.getDate(), date))
                    total += budget.getDailyAmount() * (Period.between(budget.getStart(), budget.getEnd()).getDays() + 1);
            }
        }

        return total;
    }

}

