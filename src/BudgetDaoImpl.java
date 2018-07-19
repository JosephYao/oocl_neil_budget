import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BudgetDaoImpl implements BudgetDao {
    @Override
    public List<Budget> getAllBudges() {
        List<Budget> budgets=new ArrayList<Budget>();
        Budget budget = new Budget(LocalDate.of(2012,2,5),100);
        budgets.add(budget);
        return budgets;
    }
}
