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

//    //    임시 운영 시작
//    private Boolean temporaryOperateStartTime;
//
//    //    임시 운영 종료
//    private Boolean temporaryOperateEndTime;

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

    private OperateTime (Builder builder) {
//        this.temporaryOperateStartTime = builder.temporaryOperateStartTime != null ? builder.temporaryOperateStartTime : this.temporaryOperateStartTime;
//        this.temporaryOperateEndTime = builder.temporaryOperateEndTime != null ? builder.temporaryOperateEndTime : this.temporaryOperateEndTime;
        this.weekdayAllDay = builder.weekdayAllDay != null ? builder.weekdayAllDay : this.weekdayAllDay;
        this.weekdayStartTime = builder.weekdayStartTime != null ? builder.weekdayStartTime : this.weekdayStartTime;
        this.weekdayEndTime = builder.weekdayEndTime != null ? builder.weekdayEndTime : this.weekdayEndTime;
        this.saturdayAllDay = builder.saturdayAllDay != null ? builder.saturdayAllDay : this.saturdayAllDay;
        this.saturdayStartTime = builder.saturdayStartTime != null ? builder.saturdayStartTime : this.saturdayStartTime;
        this.saturdayEndTime = builder.saturdayEndTime != null ? builder.saturdayEndTime : this.saturdayEndTime;
        this.sundayAllDay = builder.sundayAllDay != null ? builder.sundayAllDay : this.sundayAllDay;
        this.sundayStartTime = builder.sundayStartTime != null ? builder.sundayStartTime : this.sundayStartTime;
        this.sundayEndTime = builder.sundayEndTime != null ? builder.sundayEndTime : this.sundayEndTime;
        this.setting = builder.setting != null ? builder.setting : this.setting;
    }

    public static class Builder {
//        private Boolean temporaryOperateStartTime;
//        private Boolean temporaryOperateEndTime;
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

        //        기본 생성자
        public Builder () {}

//        기존 객체를 기반으로하는 생성자 (빌더 패턴에서 부분 수정을 하기 위해 사용됨)
        public Builder(OperateTime existing) {
//            this.temporaryOperateStartTime = existing.temporaryOperateStartTime;
//            this.temporaryOperateEndTime = existing.temporaryOperateEndTime;
            this.weekdayAllDay = existing.weekdayAllDay;
            this.weekdayStartTime = existing.weekdayStartTime;
            this.weekdayEndTime = existing.weekdayEndTime;
            this.saturdayAllDay = existing.saturdayAllDay;
            this.saturdayStartTime = existing.saturdayStartTime;
            this.saturdayEndTime = existing.saturdayEndTime;
            this.sundayAllDay = existing.sundayAllDay;
            this.sundayStartTime = existing.sundayStartTime;
            this.sundayEndTime = existing.sundayEndTime;
            this.setting = existing.setting;
        }

//        public Builder temporaryOperateStartTime(Boolean temporaryOperateStartTime) {
//            this.temporaryOperateStartTime = temporaryOperateStartTime;
//            return this;
//        }
//
//        public Builder temporaryOperateEndTime(Boolean temporaryOperateEndTime) {
//            this.temporaryOperateEndTime = temporaryOperateEndTime;
//            return this;
//        }

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
            return new OperateTime(this);
        }
    }
}
