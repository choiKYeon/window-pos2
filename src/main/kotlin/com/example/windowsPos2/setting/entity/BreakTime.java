package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
@Entity
public class BreakTime extends BaseEntity {

    //    브레이크 타임 임시 종료 시간
    private LocalTime temporaryEndTime;

    //    평일 브레이크 타임 설정
    //    평일 브레이크 타임 설정 여부
    private Boolean weekdayBreakTime = false;

    //    평일 브레이크 타임 시작 시간
    private LocalTime weekdayBreakTimeStart = LocalTime.of(0, 0);

    //    평일 브레이크 타임 종료 시간
    private LocalTime weekdayBreakTimeEnd = LocalTime.of(0, 0);

    //    토요일 브레이크 타임 설정
    //    토요일 브레이크 타임 설정 여부
    private Boolean saturdayBreakTime = false;

    //    토요일 브레이크 타임 시작 시간
    private LocalTime saturdayBreakTimeStart = LocalTime.of(0, 0);

    //    토요일 브레이크 타임 종료 시간
    private LocalTime saturdayBreakTimeEnd = LocalTime.of(0, 0);

    //    일요일 브레이크 타임 설정
    //    일요일 브레이크 타임 설정 여부
    private Boolean sundayBreakTime = false;

    //    일요일 브레이크 타임 시작 시간
    private LocalTime sundayBreakTimeStart = LocalTime.of(0, 0);

    //    일요일 브레이크 타임 종료 시간
    private LocalTime sundayBreakTimeEnd = LocalTime.of(0, 0);

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

//    @Builder
    public BreakTime(LocalTime temporaryEndTime,
                     Boolean weekdayBreakTime,
                     LocalTime weekdayBreakTimeStart,
                     LocalTime weekdayBreakTimeEnd,
                     Boolean saturdayBreakTime,
                     LocalTime saturdayBreakTimeStart,
                     LocalTime saturdayBreakTimeEnd,
                     Boolean sundayBreakTime,
                     LocalTime sundayBreakTimeStart,
                     LocalTime sundayBreakTimeEnd,
                     Setting setting) {
        this.temporaryEndTime = temporaryEndTime != null ? temporaryEndTime : this.temporaryEndTime;
        this.weekdayBreakTime = weekdayBreakTime != null ? weekdayBreakTime : this.weekdayBreakTime;
        this.weekdayBreakTimeStart = weekdayBreakTimeStart != null ? weekdayBreakTimeStart : this.weekdayBreakTimeStart;
        this.weekdayBreakTimeEnd = weekdayBreakTimeEnd != null ? weekdayBreakTimeEnd : this.weekdayBreakTimeEnd;
        this.saturdayBreakTime = saturdayBreakTime != null ? saturdayBreakTime : this.saturdayBreakTime;
        this.saturdayBreakTimeStart = saturdayBreakTimeStart != null ? saturdayBreakTimeStart : this.saturdayBreakTimeStart;
        this.saturdayBreakTimeEnd = saturdayBreakTimeEnd != null ? saturdayBreakTimeEnd : this.saturdayBreakTimeEnd;
        this.sundayBreakTime = sundayBreakTime != null ? sundayBreakTime : this.sundayBreakTime;
        this.sundayBreakTimeStart = sundayBreakTimeStart != null ? sundayBreakTimeStart : this.sundayBreakTimeStart;
        this.sundayBreakTimeEnd = sundayBreakTimeEnd != null ? sundayBreakTimeEnd : this.sundayBreakTimeEnd;
        this.setting = setting != null ? setting : this.setting;
    }

    public static class Builder {
        private LocalTime temporaryEndTime;
        private Boolean weekdayBreakTime;
        private LocalTime weekdayBreakTimeStart;
        private LocalTime weekdayBreakTimeEnd;
        private Boolean saturdayBreakTime;
        private LocalTime saturdayBreakTimeStart;
        private LocalTime saturdayBreakTimeEnd;
        private Boolean sundayBreakTime;
        private LocalTime sundayBreakTimeStart;
        private LocalTime sundayBreakTimeEnd;
        private Setting setting;

        public Builder temporaryEndTime(LocalTime temporaryEndTime) {
            this.temporaryEndTime = temporaryEndTime;
            return this;
        }

        public Builder weekdayBreakTime(Boolean weekdayBreakTime) {
            this.weekdayBreakTime = weekdayBreakTime;
            return this;
        }

        public Builder weekdayBreakTimeStart(LocalTime weekdayBreakTimeStart) {
            this.weekdayBreakTimeStart = weekdayBreakTimeStart;
            return this;
        }

        public Builder weekdayBreakTimeEnd(LocalTime weekdayBreakTimeEnd) {
            this.weekdayBreakTimeEnd = weekdayBreakTimeEnd;
            return this;
        }

        public Builder saturdayBreakTime(Boolean saturdayBreakTime) {
            this.saturdayBreakTime = saturdayBreakTime;
            return this;
        }

        public Builder saturdayBreakTimeStart(LocalTime saturdayBreakTimeStart) {
            this.saturdayBreakTimeStart = saturdayBreakTimeStart;
            return this;
        }

        public Builder saturdayBreakTimeEnd(LocalTime saturdayBreakTimeEnd) {
            this.saturdayBreakTimeEnd = saturdayBreakTimeEnd;
            return this;
        }

        public Builder sundayBreakTime(Boolean sundayBreakTime) {
            this.sundayBreakTime = sundayBreakTime;
            return this;
        }

        public Builder sundayBreakTimeStart(LocalTime sundayBreakTimeStart) {
            this.sundayBreakTimeStart = sundayBreakTimeStart;
            return this;
        }

        public Builder sundayBreakTimeEnd(LocalTime sundayBreakTimeEnd) {
            this.sundayBreakTimeEnd = sundayBreakTimeEnd;
            return this;
        }

        public Builder setting(Setting setting) {
            this.setting = setting;
            return this;
        }

        public BreakTime build() {
            return new BreakTime(temporaryEndTime, weekdayBreakTime, weekdayBreakTimeStart, weekdayBreakTimeEnd, saturdayBreakTime, saturdayBreakTimeStart, saturdayBreakTimeEnd, sundayBreakTime, sundayBreakTimeStart, sundayBreakTimeEnd, setting);
        }
    }
}
