package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        //Проверить, что за вторник не вернулось занятий
        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        Iterator<List<TrainingSession>> iterator = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).values().iterator();
        assertEquals(new TimeOfDay(13, 0), iterator.next().getFirst().getTimeOfDay());
        assertEquals(new TimeOfDay(20, 0), iterator.next().getFirst().getTimeOfDay());

        // Проверить, что за вторник не вернулось занятий
        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessionsInOneDay() {
        Timetable timetable = new Timetable();

        Coach coachIvanov = new Coach("Иванов", "Иван", "Иванович");
        Coach coachPetrov = new Coach("Петров", "Петр", "Петрович");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachIvanov,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0)));

        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachPetrov,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0)));

        // Проверить, что в четверг в 20:00 одновременно проходят два занятия
        assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY, new TimeOfDay(20, 0)).size());

        // Проверить, что в четверг в 20:00 одновременно проходят два занятия у двух разных тренеров
        assertEquals(coachIvanov, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY, new TimeOfDay(20, 0)).get(0).getCoach());
        assertEquals(coachPetrov, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY, new TimeOfDay(20, 0)).get(1).getCoach());

        // Проверить, что заведена правильная группа
        assertEquals(groupAdult, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY, new TimeOfDay(20, 0)).get(0).getGroup());
        assertEquals(groupAdult, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY, new TimeOfDay(20, 0)).get(1).getGroup());
    }

    @Test
    void testGetCountByCoach() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(13, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(10, 0)));

        // Проверить, что для тренера coach возвращается значение равное кол-ву заданных тренировок (4)
        assertEquals(4, timetable.getCountByCoaches().getFirst().getCount());
    }

    @Test
    void testGetCountByCoachesDesc() {
        Timetable timetable = new Timetable();

        Coach coachIvanov = new Coach("Иванов", "Иван", "Иванович");
        Coach coachPetrov = new Coach("Петров", "Петр", "Петрович");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachPetrov,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0)));

        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachIvanov,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachIvanov,
                DayOfWeek.TUESDAY, new TimeOfDay(20, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachIvanov,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0)));

        // Проверить, что было добавлено 2 тренера
        assertEquals(2, timetable.getCountByCoaches().size());

        // Проверить, что для тренера coachIvanov (по порядку от большего к меньшему) возвращается значение
        // равное кол-ву заданных тренировок (3)
        Iterator<CounterOfTrainings> iterator = timetable.getCountByCoaches().iterator();
        assertEquals(3, iterator.next().getCount());

        // Проверить, что для тренера coachPetrov (по порядку от большего к меньшему) возвращается значение
        // равное кол-ву заданных тренировок (1)
        assertEquals(1, iterator.next().getCount());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Coach coachIvanov = new Coach("Иванов", "Иван", "Иванович");
        Coach coachPetrov = new Coach("Петров", "Петр", "Петрович");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachPetrov,
                DayOfWeek.SATURDAY, new TimeOfDay(21, 0)));

        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachIvanov,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0)));

        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coachPetrov,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0)));

        // Проверить, что для что это именно тренер coachPetrov
        assertEquals(coachPetrov, timetable.getCountByCoaches().get(0).getCoach());
        // И у него 2 занятия в неделю
        assertEquals(2, timetable.getCountByCoaches().get(0).getCount());

        // Проверить, что для что это именно тренер coachIvanov
        assertEquals(coachIvanov, timetable.getCountByCoaches().get(1).getCoach());
        // И у него 1 занятие в неделю
        assertEquals(1, timetable.getCountByCoaches().get(1).getCount());
    }
}
