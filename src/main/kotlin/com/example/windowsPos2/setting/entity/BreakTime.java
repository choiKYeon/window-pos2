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
    private BreakTime(Builder builder) {
        this.temporaryEndTime = builder.temporaryEndTime != null ? builder.temporaryEndTime : this.temporaryEndTime;
        this.weekdayBreakTime = builder.weekdayBreakTime != null ? builder.weekdayBreakTime : this.weekdayBreakTime;
        this.weekdayBreakTimeStart = builder.weekdayBreakTimeStart != null ? builder.weekdayBreakTimeStart : this.weekdayBreakTimeStart;
        this.weekdayBreakTimeEnd = builder.weekdayBreakTimeEnd != null ? builder.weekdayBreakTimeEnd : this.weekdayBreakTimeEnd;
        this.saturdayBreakTime = builder.saturdayBreakTime != null ? builder.saturdayBreakTime : this.saturdayBreakTime;
        this.saturdayBreakTimeStart = builder.saturdayBreakTimeStart != null ? builder.saturdayBreakTimeStart : this.saturdayBreakTimeStart;
        this.saturdayBreakTimeEnd = builder.saturdayBreakTimeEnd != null ? builder.saturdayBreakTimeEnd : this.saturdayBreakTimeEnd;
        this.sundayBreakTime = builder.sundayBreakTime != null ? builder.sundayBreakTime : this.sundayBreakTime;
        this.sundayBreakTimeStart = builder.sundayBreakTimeStart != null ? builder.sundayBreakTimeStart : this.sundayBreakTimeStart;
        this.sundayBreakTimeEnd = builder.sundayBreakTimeEnd != null ? builder.sundayBreakTimeEnd : this.sundayBreakTimeEnd;
        this.setting = builder.setting != null ? builder.setting : this.setting;
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

//        기본 생성자
        public Builder () {}

        // 기존 객체를 기반으로 하는 생성자 (빌더 패턴에서 부분 수정을 하기 위해 사용됨)
        public Builder(BreakTime existing) {
            this.temporaryEndTime = existing.getTemporaryEndTime();
            this.weekdayBreakTime = existing.getWeekdayBreakTime();
            this.weekdayBreakTimeStart = existing.getWeekdayBreakTimeStart();
            this.weekdayBreakTimeEnd = existing.getWeekdayBreakTimeEnd();
            this.saturdayBreakTime = existing.getSaturdayBreakTime();
            this.saturdayBreakTimeStart = existing.getSaturdayBreakTimeStart();
            this.saturdayBreakTimeEnd = existing.getSaturdayBreakTimeEnd();
            this.sundayBreakTime = existing.getSundayBreakTime();
            this.sundayBreakTimeStart = existing.getSundayBreakTimeStart();
            this.sundayBreakTimeEnd = existing.getSundayBreakTimeEnd();
            this.setting = existing.getSetting();
        }

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
            return new BreakTime(this);
        }
    }
}
