package test.hunt_test.utils;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class DateUtils {
    public static LocalDate getRandomDate() {
        return LocalDate.ofEpochDay(ThreadLocalRandom.current().nextInt(0, (int) LocalDate.now().toEpochDay()));

    }
}
