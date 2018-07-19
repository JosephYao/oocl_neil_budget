package com.oocl.course;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetTest {

    StubBudgetDao budgetDao = new StubBudgetDao();
    BudgetService budgetService = new BudgetService(budgetDao);

    @Test
    public void no_budget() {
        givenBudgets();

        double actual = budgetService.queryBudget(
                of(2018, 6, 4),
                of(2018, 6, 4));

        assertEquals(0, actual, 0.01);
    }

    @Test
    public void start_is_after_end() {
        givenBudgets(budget(2018, 6, 300));

        double actual = budgetService.queryBudget(
                of(2018, 6, 4),
                of(2018, 5, 4));

        assertEquals(0, actual, 0.01);
    }

    @Test
    public void sameDay() {
        givenBudgets(budget(2018, 6, 300));

        double actual = budgetService.queryBudget(
                of(2018, 6, 4),
                of(2018, 6, 4));

        assertEquals(10, actual, 0.01);
    }

    @Test
    public void sameMonth() {
        givenBudgets(budget(2018, 6, 300));

        double actual = budgetService.queryBudget(
                of(2018, 6, 2),
                of(2018, 6, 7));

        assertEquals(60, actual, 0.01);
    }

    @Test
    public void start_is_before_month_first_day() {
        givenBudgets(budget(2018, 6, 300));

        double actual = budgetService.queryBudget(
                of(2018, 5, 20),
                of(2018, 6, 7));

        assertEquals(70, actual);
    }

    @Test
    public void end_is_after_month_last_day() {
        givenBudgets(budget(2018, 6, 300));

        double actual = budgetService.queryBudget(
                of(2018, 6, 20),
                of(2018, 7, 7));

        assertEquals(110, actual, 0.01);
    }

    @Test
    public void end_is_before_month_first_day() {
        givenBudgets(budget(2018, 6, 300));

        double actual = budgetService.queryBudget(
                of(2018, 5, 20),
                of(2018, 5, 23));

        assertEquals(0, actual, 0.01);
    }

    @Test
    public void start_is_after_month_last_day() {
        givenBudgets(budget(2018, 6, 300));

        double actual = budgetService.queryBudget(
                of(2018, 7, 20),
                of(2018, 7, 23));

        assertEquals(0, actual, 0.01);
    }

    @Test
    public void start_and_end_cover_the_whole_month() {
        givenBudgets(budget(2018, 6, 300));

        double actual = budgetService.queryBudget(
                of(2018, 5, 20),
                of(2018, 7, 23));

        assertEquals(300, actual, 0.01);
    }

    @Test
    public void adjacentMonth() {
        givenBudgets(
                budget(2018, 6, 300),
                budget(2018, 7, 310));

        double actual = budgetService.queryBudget(
                of(2018, 6, 15),
                of(2018, 7, 10));

        assertEquals(260, actual, 0.01);
    }

    @Test
    public void DiscreteMonth() {
        givenBudgets(
                budget(2018, 6, 300),
                budget(2018, 8, 310));

        double actual = budgetService.queryBudget(
                of(2018, 6, 15),
                of(2018, 8, 12));

        assertEquals(280, actual, 0.01);
    }

    @Test
    public void differentYear() {
        givenBudgets(budget(2018, 6, 300));

        double actual = budgetService.queryBudget(of(2015, 6, 7), of(2016, 6, 4));

        assertEquals(0, actual, 0.01);
    }

    private void givenBudgets(final Budget... budgets) {
        budgetDao.setBudgets(Arrays.asList(budgets));
    }

    private Budget budget(int year, int month, int amount) {
        return new Budget(of(year, month, 4), amount);
    }

    private class StubBudgetDao implements BudgetDao {


        private List<Budget> budgets;

        public void setBudgets(List<Budget> budgets) {
            this.budgets = budgets;
        }

        @Override
        public List<Budget> getAllBudges() {
            return budgets;
        }
    }
}
