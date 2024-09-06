package ru.anafro.wondercrates.utils.time;

public class TimeSpan {
    public static final TimeSpan INSTANT = TimeSpan.seconds(0);
    public static final TimeSpan TICK = TimeSpan.ticks(1);
    public static final TimeSpan SECOND = TimeSpan.seconds(1);
    private static final int TICKS_IN_A_SECOND = 20;
    private final int ticks;

    public TimeSpan(int ticks) {
        this.ticks = ticks;
    }

    public static TimeSpan seconds(int seconds) {
        return new TimeSpan(TICKS_IN_A_SECOND * seconds);
    }

    public static TimeSpan ticks(int ticks) {
        return new TimeSpan(ticks);
    }

    public int getTicks() {
        return ticks;
    }
}
