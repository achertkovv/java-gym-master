package ru.yandex.practicum.gym;

import java.util.*;

/// получить все тренировки за конкретный день недели

public class Timetable {

    // Хеш-таблица с ключом — днём недели (для каждого дня недели получать все тренировки в отсортированном по времени начала порядке)
    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        // сохраняем занятие в расписании
        // Хранить в уже отсортированном виде для получения
        // отсортированных по времени тренировок за день
        TreeMap<TimeOfDay, List<TrainingSession>> training;
        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            training = timetable.get(trainingSession.getDayOfWeek());
        } else {
            training = new TreeMap<>();
        }
        // метод падает с NPE, если за указанный день вообще нет тренировок: timetable.get(dayOfWeek)
        // вернет null, и следующая строка обратится к нему.
        // Это не редкий сценарий, а обычный запрос администратора про пустой день.
        if (training == null) return;
        // Не забудьте учесть, что в одно время может
        // начинаться сразу несколько тренировок!
        List<TrainingSession> trainingsList;
        if (training.containsKey(trainingSession.getTimeOfDay())) {
            trainingsList = training.get(trainingSession.getTimeOfDay());
        } else {
            trainingsList = new ArrayList<>();
        }
        // метод падает с NPE, если за указанный день вообще нет тренировок: timetable.get(dayOfWeek)
        // вернет null, и следующая строка обратится к нему.
        // Это не редкий сценарий, а обычный запрос администратора про пустой день.
        if (trainingsList == null) return;
        trainingsList.add(trainingSession);
        training.put(trainingSession.getTimeOfDay(), trainingsList);
        timetable.put(trainingSession.getDayOfWeek(), training);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessions = timetable.get(dayOfWeek);
        // Добавьте проверку и возвращайте пустой список.
        if (trainingSessions == null) return new ArrayList<>();
        return trainingSessions.get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, CounterOfTrainings> map = new HashMap<>();
        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> entryDayOfWeek : timetable.entrySet()) {
            for (Map.Entry<TimeOfDay, List<TrainingSession>> entryTimeOfDay : entryDayOfWeek.getValue().entrySet()) {
                for (TrainingSession trainingSession : entryTimeOfDay.getValue()) {
                    if (!map.containsKey(trainingSession.getCoach()))
                        map.put(trainingSession.getCoach(), new CounterOfTrainings(trainingSession.getCoach()));
                    map.get(trainingSession.getCoach()).incrementCountOfTrainings();
                }
            }
        }

        List<CounterOfTrainings> trainingsByCount = new ArrayList<>(map.values());
        Collections.sort(trainingsByCount);
        return trainingsByCount;
    }
 }
