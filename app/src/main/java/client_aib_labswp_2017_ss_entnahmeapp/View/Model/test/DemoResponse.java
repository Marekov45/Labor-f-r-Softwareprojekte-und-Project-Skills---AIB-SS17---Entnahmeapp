package client_aib_labswp_2017_ss_entnahmeapp.View.Model.test;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 09.06.2017.
 */
public class DemoResponse {

    @SerializedName("hour")
    public int hour;
    @SerializedName("minute")
    public int minute;
    @SerializedName("second")
    public int second;
    @SerializedName("nano")
    public int nano;
    @SerializedName("dayOfYear")
    public int dayofyear;
    @SerializedName("dayOfWeek")
    public String dayofweek;
    @SerializedName("month")
    public String month;
    @SerializedName("dayOfMonth")
    public int dayofmonth;
    @SerializedName("year")
    public int year;
    @SerializedName("monthValue")
    public int monthvalue;
    @SerializedName("chronology")
    public Chronology chronology;

    @Override
    public String toString() {
        return dayofmonth + "." + month + "." + year + " " + hour + ":" + minute + ":" + second;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getNano() {
        return nano;
    }

    public void setNano(int nano) {
        this.nano = nano;
    }

    public int getDayofyear() {
        return dayofyear;
    }

    public void setDayofyear(int dayofyear) {
        this.dayofyear = dayofyear;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDayofmonth() {
        return dayofmonth;
    }

    public void setDayofmonth(int dayofmonth) {
        this.dayofmonth = dayofmonth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonthvalue() {
        return monthvalue;
    }

    public void setMonthvalue(int monthvalue) {
        this.monthvalue = monthvalue;
    }

    public Chronology getChronology() {
        return chronology;
    }

    public void setChronology(Chronology chronology) {
        this.chronology = chronology;
    }

    public static class Chronology {
        @SerializedName("id")
        public String id;
        @SerializedName("calendarType")
        public String calendartype;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCalendartype() {
            return calendartype;
        }

        public void setCalendartype(String calendartype) {
            this.calendartype = calendartype;
        }
    }

}
