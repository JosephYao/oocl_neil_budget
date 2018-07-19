import java.time.LocalDate;

public class  Budget {
    private LocalDate date;
    double amount;

    public Budget(LocalDate date,double amount){
        this.amount=amount;
        this.date=date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
