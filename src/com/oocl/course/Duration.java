package com.oocl.course;

import java.time.LocalDate;
import java.time.YearMonth;

public class Duration {
    private final LocalDate start;
    private final LocalDate end;

    public Duration(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public boolean isSameMonth() {
        return YearMonth.from(start).equals(YearMonth.from(end));
    }
}
