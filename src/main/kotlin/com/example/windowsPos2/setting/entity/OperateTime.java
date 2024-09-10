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
//@SuperBuilder
@Entity
public class OperateTime extends BaseEntity {

    //    임시 운영 시작
    private Boolean temporaryOperateStartTime;

    //    임시 운영 종료
    private Boolean temporaryOperateEndTime;

    //    평일 24시간 영업
    private Boolean weekdayAllDay = true;

    //    평일 시작 시간
    private LocalTime weekdayStartTime = LocalTime.of(0, 0);

    //    평일 종료 시간
    private LocalTime weekdayEndTime = LocalTime.of(23, 59);

    //    토요일 24시간 영업
    private Boolean saturdayAllDay = true;

    //    토요일 시작 시간
    private LocalTime saturdayStartTime = LocalTime.of(0, 0);

    //    토요일 종료 시간
    private LocalTime saturdayEndTime = LocalTime.of(23, 59);

    //    일요일 24시간 영업
    private Boolean sundayAllDay = true;

    //    일요일 시작 시간
    private LocalTime sundayStartTime = LocalTime.of(0, 0);

    //    일요일 종료 시간
    private LocalTime sundayEndTime = LocalTime.of(23, 59);

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

    public OperateTime (Boolean temporaryOperateStartTime, Boolean temporaryOperateEndTime,
                        Boolean weekdayAllDay, LocalTime weekdayStartTime, LocalTime weekdayEndTime,
                        Boolean saturdayAllDay, LocalTime saturdayStartTime, LocalTime saturdayEndTime,
                        Boolean sundayAllDay, LocalTime sundayStartTime, LocalTime sundayEndTime, Setting setting) {
        this.temporaryOperateStartTime = temporaryOperateStartTime != null ? temporaryOperateStartTime : this.temporaryOperateStartTime;
        this.temporaryOperateEndTime = temporaryOperateEndTime != null ? temporaryOperateEndTime : this.temporaryOperateEndTime;
        this.weekdayAllDay = weekdayAllDay != null ? weekdayAllDay : this.weekdayAllDay;
        this.weekdayStartTime = weekdayStartTime != null ? weekdayStartTime : this.weekdayStartTime;
        this.weekdayEndTime = weekdayEndTime != null ? weekdayEndTime : this.weekdayEndTime;
        this.saturdayAllDay = saturdayAllDay != null ? saturdayAllDay : this.saturdayAllDay;
        this.saturdayStartTime = saturdayStartTime != null ? saturdayStartTime : this.saturdayStartTime;
        this.saturdayEndTime = saturdayEndTime != null ? saturdayEndTime : this.saturdayEndTime;
        this.sundayAllDay = sundayAllDay != null ? sundayAllDay : this.sundayAllDay;
        this.sundayStartTime = sundayStartTime != null ? sundayStartTime : this.sundayStartTime;
        this.sundayEndTime = sundayEndTime != null ? sundayEndTime : this.sundayEndTime;
        this.setting = setting != null ? setting : this.setting;
    }

    public static class Builder {
        private Boolean temporaryOperateStartTime;
        private Boolean temporaryOperateEndTime;
        private Boolean weekdayAllDay;
        private LocalTime weekdayStartTime;
        private LocalTime weekdayEndTime;
        private Boolean saturdayAllDay;
        private LocalTime saturdayStartTime;
        private LocalTime saturdayEndTime;
        private Boolean sundayAllDay;
        private LocalTime sundayStartTime;
        private LocalTime sundayEndTime;
        private Setting setting;

        public Builder temporaryOperateStartTime(Boolean temporaryOperateStartTime) {
            this.temporaryOperateStartTime = temporaryOperateStartTime;
            return this;
        }

        public Builder temporaryOperateEndTime(Boolean temporaryOperateEndTime) {
            this.temporaryOperateEndTime = temporaryOperateEndTime;
            return this;
        }

        public Builder weekdayAllDay(Boolean weekdayAllDay) {
            this.weekdayAllDay = weekdayAllDay;
            return this;
        }

        public Builder weekdayStartTime(LocalTime weekdayStartTime) {
            this.weekdayStartTime = weekdayStartTime;
            return this;
        }

        public Builder weekdayEndTime(LocalTime weekdayEndTime) {
            this.weekdayEndTime = weekdayEndTime;
            return this;
        }

        public Builder saturdayAllDay(Boolean saturdayAllDay) {
            this.saturdayAllDay = saturdayAllDay;
            return this;
        }

        public Builder saturdayStartTime(LocalTime saturdayStartTime) {
            this.saturdayStartTime = saturdayStartTime;
            return this;
        }

        public Builder saturdayEndTime(LocalTime saturdayEndTime) {
            this.saturdayEndTime = saturdayEndTime;
            return this;
        }

        public Builder sundayAllDay(Boolean sundayAllDay) {
            this.sundayAllDay = sundayAllDay;
            return this;
        }

        public Builder sundayStartTime(LocalTime sundayStartTime) {
            this.sundayStartTime = sundayStartTime;
            return this;
        }

        public Builder sundayEndTime(LocalTime sundayEndTime) {
            this.sundayEndTime = sundayEndTime;
            return this;
        }

        public Builder setting(Setting setting) {
            this.setting = setting;
            return this;
        }

        public OperateTime build() {
            return new OperateTime(temporaryOperateStartTime, temporaryOperateEndTime, weekdayAllDay, weekdayStartTime, weekdayEndTime,
                    saturdayAllDay, saturdayStartTime, saturdayEndTime, sundayAllDay, sundayStartTime, sundayEndTime, setting);
        }
    }
}
