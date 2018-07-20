package com.oocl.course;

import java.time.LocalDate;
import java.time.Period;

public class Duration {
    private final LocalDate start;
    private final LocalDate end;

    public Duration(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    private int getDays() {
        if (start.isAfter(end))
            return 0;

        return Period.between(start, end).getDays() + 1;
    }

    public int getOverlappingDays(Duration another) {
        LocalDate overlappingEnd = end.isBefore(another.end) ? end : another.end;
        LocalDate overlappingStart = start.isAfter(another.start) ? start : another.start;
        return new Duration(overlappingStart, overlappingEnd).getDays();
    }
}
