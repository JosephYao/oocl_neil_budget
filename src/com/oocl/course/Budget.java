package com.oocl.course;

import java.time.LocalDate;

public class  Budget {
    private LocalDate date;
    double amount;

    public Budget(LocalDate date,double amount){
        this.amount=amount;
        this.date=date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private double getDailyAmount() {
        return amount / date.lengthOfMonth();
    }

    private LocalDate getEnd() {
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    private LocalDate getStart() {
        return date.withDayOfMonth(1);
    }

    private Duration getDuration() {
        return new Duration(getStart(), getEnd());
    }

    public double getOverlappingAmount(Duration duration) {
        return getDailyAmount() * duration.getOverlappingDays(getDuration());
    }
}
