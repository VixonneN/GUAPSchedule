package ru.vlad805.guap.schedule.api;

import java.util.List;

/**
 * Created by arktic on 08.11.15.
 */
public class Schedule {

    public static class Response {

        public static class Day {

            public static class Couple {

                public int coupleId;
                public String type;
                public String subject;
                public List<String> groups;
                public int parity;
                public String build;
                public List<String> audiences;
                public String teacher;

            }

            public int dayId;
            public String title;
            public List<Couple> couples;

        }

        public String parseDate;
        public List<Day> schedule;

    }

    public Response response;

}
