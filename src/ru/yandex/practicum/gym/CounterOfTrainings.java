package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable {
    private final Coach coach;
    private int count;

    public CounterOfTrainings(Coach coach) {
        this.coach = coach;
        this.count = 0;
    }

    public void incrementCountOfTrainings() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public Coach getCoach() {
        return coach;
    }

    @Override
    public int compareTo(Object o) {
        CounterOfTrainings counterOfTrainings = (CounterOfTrainings) o;
        return counterOfTrainings.count - this.count;
    }
}
