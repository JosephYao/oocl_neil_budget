
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetTest {

    @Test
    public void sameDay(){
        StubBudgetDao  budgetDao= new StubBudgetDao();

        budgetDao.setBudgets(new ArrayList<Budget>(){{
            add(new Budget(LocalDate.of(2017,6,4),300));
            add(new Budget(LocalDate.of(2017,9,4),600));
        }});
        BudgetService budgetService = new BudgetService(budgetDao);

        LocalDate start = LocalDate.of(2017,6,4);
        LocalDate end = LocalDate.of(2017,6,4);

        assertEquals(10,budgetService.queryBudget(start,end));
    }

    @Test
    public void sameMonth(){
        StubBudgetDao  budgetDao= new StubBudgetDao();

        budgetDao.setBudgets(new ArrayList<Budget>(){{
            add(new Budget(LocalDate.of(2017,6,4),300));
            add(new Budget(LocalDate.of(2017,9,4),600));
        }});
        BudgetService budgetService = new BudgetService(budgetDao);

        LocalDate start = LocalDate.of(2017,2,2);
        LocalDate end = LocalDate.of(2017,2,7);

        assertEquals(0,budgetService.queryBudget(start,end));
    }

    @Test
    public void adjacentMonth(){
        StubBudgetDao  budgetDao= new StubBudgetDao();

        budgetDao.setBudgets(new ArrayList<Budget>(){{
            add(new Budget(LocalDate.of(2017,6,4),300));
            add(new Budget(LocalDate.of(2017,9,4),600));
        }});
        BudgetService budgetService = new BudgetService(budgetDao);

        LocalDate start = LocalDate.of(2017,6,1);
        LocalDate end = LocalDate.of(2017,7,8);

        assertEquals(300,budgetService.queryBudget(start,end));
    }

    @Test
    public void DiscreteMonth(){
        StubBudgetDao  budgetDao= new StubBudgetDao();

        budgetDao.setBudgets(new ArrayList<Budget>(){{
            add(new Budget(LocalDate.of(2017,6,4),300));
            add(new Budget(LocalDate.of(2017,9,4),600));
        }});
        BudgetService budgetService = new BudgetService(budgetDao);

        LocalDate start = LocalDate.of(2017,6,4);
        LocalDate end = LocalDate.of(2017,9,4);

        assertEquals(350,budgetService.queryBudget(start,end));
    }

    @Test
    public void differentYear(){
        StubBudgetDao  budgetDao= new StubBudgetDao();

        budgetDao.setBudgets(new ArrayList<Budget>(){{
            add(new Budget(LocalDate.of(2017,6,4),300));
            add(new Budget(LocalDate.of(2017,9,4),600));
        }});
        BudgetService budgetService = new BudgetService(budgetDao);

        LocalDate start = LocalDate.of(2017,2,4);
        LocalDate end = LocalDate.of(2018,2,4);

        assertEquals(900,budgetService.queryBudget(start,end));
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
