package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.example.windowsPos2.setting.settingEnum.RegularClosedDays;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class RegularHoliday extends BaseEntity {

    //    무슨 요일인지
    @Setter
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek = DayOfWeek.MONDAY;

    //    매 주 언제 정기휴무인지 ex) 첫째 주 / 둘째 주 / 매 주
    @Setter
    @Enumerated(EnumType.STRING)
    private RegularClosedDays regularClosedDays = RegularClosedDays.FIRST;

    //    몇 주 무슨 요일 정기 휴무인지
    public boolean isHoliday(LocalDate date) {
        DayOfWeek localDayOfWeek = date.getDayOfWeek();
        DayOfWeek holidayDayOfWeek = DayOfWeek.valueOf(localDayOfWeek.name());

        if (holidayDayOfWeek.equals(dayOfWeek)) {
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekOfMonth = date.get(weekFields.weekOfMonth());

            switch (regularClosedDays) {
                case EVERY_WEEK:
                    return true;
                case FIRST:
                    return weekOfMonth == 1;
                case SECOND:
                    return weekOfMonth == 2;
                case THIRD:
                    return weekOfMonth == 3;
                case FOURTH:
                    return weekOfMonth == 4;
                default:
                    return false;
            }
        }
        return false;
    }

    @Setter
    @ManyToOne
    @JoinColumn(name = "closed_days_id")
    @JsonBackReference
    private ClosedDays closedDays;
}
