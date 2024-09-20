package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.example.windowsPos2.setting.settingEnum.RegularClosedDays;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
@Entity
public class RegularHoliday extends BaseEntity {

    //    무슨 요일인지
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek = DayOfWeek.MONDAY;

    //    매 주 언제 정기휴무인지 ex) 첫째 주 / 둘째 주 / 매 주
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

    @ManyToOne
    @JoinColumn(name = "closed_days_id")
    @JsonBackReference
    private ClosedDays closedDays;

    private RegularHoliday (Builder builder) {
        this.dayOfWeek = builder.dayOfWeek != null ? builder.dayOfWeek : this.dayOfWeek;
        this.regularClosedDays = builder.regularClosedDays != null ? builder.regularClosedDays : this.regularClosedDays;
        this.closedDays = builder.closedDays != null ? builder.closedDays : this.closedDays;
    }

    public static class Builder {
        private DayOfWeek dayOfWeek;
        private RegularClosedDays regularClosedDays;
        private ClosedDays closedDays;

        //        기본 생성자
        public Builder () {}

//        기존 객체를 기반으로하는 생성자 (빌더 패턴에서 부분 수정을 하기 위해 사용됨)
        public Builder (RegularHoliday regularHoliday) {
            this.dayOfWeek = regularHoliday.dayOfWeek;
            this.regularClosedDays = regularHoliday.regularClosedDays;
            this.closedDays = regularHoliday.closedDays;
        }

        public Builder dayOfWeek(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public Builder regularClosedDays(RegularClosedDays regularClosedDays) {
            this.regularClosedDays = regularClosedDays;
            return this;
        }

        public Builder closedDays(ClosedDays closedDays) {
            this.closedDays = closedDays;
            return this;
        }

        public RegularHoliday build() {
            return new RegularHoliday(this);
        }
    }
}