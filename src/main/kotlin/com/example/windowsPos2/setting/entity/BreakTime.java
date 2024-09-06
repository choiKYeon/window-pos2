package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class BreakTime extends BaseEntity {

    //    브레이크 타임 임시 종료 시간
    @Setter
    private LocalTime temporaryEndTime;

    //    평일 브레이크 타임 설정
    //    평일 브레이크 타임 설정 여부
    @Setter
    private Boolean weekdayBreakTime = false;

    //    평일 브레이크 타임 시작 시간
    @Setter
    private LocalTime weekdayBreakTimeStart = LocalTime.of(0, 0);

    //    평일 브레이크 타임 종료 시간
    @Setter
    private LocalTime weekdayBreakTimeEnd = LocalTime.of(0, 0);

    //    토요일 브레이크 타임 설정
    //    토요일 브레이크 타임 설정 여부
    @Setter
    private Boolean saturdayBreakTime = false;

    //    토요일 브레이크 타임 시작 시간
    @Setter
    private LocalTime saturdayBreakTimeStart = LocalTime.of(0, 0);

    //    토요일 브레이크 타임 종료 시간
    @Setter
    private LocalTime saturdayBreakTimeEnd = LocalTime.of(0, 0);

    //    일요일 브레이크 타임 설정
    //    일요일 브레이크 타임 설정 여부
    @Setter
    private Boolean sundayBreakTime = false;

    //    일요일 브레이크 타임 시작 시간
    @Setter
    private LocalTime sundayBreakTimeStart = LocalTime.of(0, 0);

    //    일요일 브레이크 타임 종료 시간
    @Setter
    private LocalTime sundayBreakTimeEnd = LocalTime.of(0, 0);

    @OneToOne
    @JoinColumn(name = "setting_id")
    @Setter
    private Setting setting;
}
