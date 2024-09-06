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

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class EstimatedCookingTime extends BaseEntity {

    //    예상 조리시간 자동 설정
    @Setter
    private Boolean estimatedCookingTimeControl = false;

    //    예상 조리시간
    @Setter
    private Integer estimatedCookingTime = 0;

    @Setter
    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}
