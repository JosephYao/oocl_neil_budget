package com.oocl.course;

import java.time.LocalDate;
import java.time.Period;
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

        if (new Duration(duration.getStart(), duration.getEnd()).isSameMonth()) {
            for (Budget budget : budgets) {
                if (new Duration(budget.getDate(), duration.getStart()).isSameMonth()) {
                    return budget.getDailyAmount() * (Period.between(duration.getStart(), duration.getEnd()).getDays() + 1);
                }
            }
            return 0;
        }

        double total = 0;
        for (Budget budget : budgets) {
            if (new Duration(budget.getDate(), duration.getStart()).isSameMonth()) {
                total += budget.getDailyAmount() * (Period.between(duration.getStart(), budget.getEnd()).getDays() + 1);
            }
            if (new Duration(budget.getDate(), duration.getEnd()).isSameMonth()) {
                total += budget.getDailyAmount() * (Period.between(budget.getStart(), duration.getEnd()).getDays() + 1);
            }

            for (LocalDate date = duration.getStart().plusMonths(1); !new Duration(date, duration.getEnd()).isSameMonth(); date = date.plusMonths(1)) {
                if (new Duration(budget.getDate(), date).isSameMonth())
                    total += budget.getDailyAmount() * (Period.between(budget.getStart(), budget.getEnd()).getDays() + 1);
            }
        }

        return total;
    }

}

