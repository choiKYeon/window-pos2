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
public class OperatePause extends BaseEntity {

    // 영업 일시 정지 시작 시간
    @Setter
    private LocalTime salesPauseStartTime = LocalTime.of(0, 0);

    // 영업 일시 정지 종료 시간
    @Setter
    private LocalTime salesPauseEndTime = LocalTime.of(0, 0);

    @Setter
    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}
