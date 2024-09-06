package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class OperateTime extends BaseEntity {

    //    임시 운영 시작
    @Setter
    private Boolean temporaryOperateStartTime = false;

    //    임시 운영 종료
    @Setter
    private Boolean temporaryOperateEndTime = false;

    //    임시 요일
    @Setter
    @Enumerated(EnumType.STRING)
    private DayOfWeek temporaryDay;

    //    평일 24시간 영업
    @Setter
    private Boolean weekdayAllDay = true;

    //    평일 시작 시간
    @Setter
    private LocalTime weekdayStartTime = LocalTime.of(0, 0);

    //    평일 종료 시간
    @Setter
    private LocalTime weekdayEndTime = LocalTime.of(23, 59);

    //    토요일 24시간 영업
    @Setter
    private Boolean saturdayAllDay = true;

    //    토요일 시작 시간
    @Setter
    private LocalTime saturdayStartTime = LocalTime.of(0, 0);

    //    토요일 종료 시간
    @Setter
    private LocalTime saturdayEndTime = LocalTime.of(23, 59);

    //    일요일 24시간 영업
    @Setter
    private Boolean sundayAllDay = true;

    //    일요일 시작 시간
    @Setter
    private LocalTime sundayStartTime = LocalTime.of(0, 0);

    //    일요일 종료 시간
    @Setter
    private LocalTime sundayEndTime = LocalTime.of(23, 59);

    @Setter
    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}
