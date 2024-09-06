package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.example.windowsPos2.member.entity.Member;
import com.example.windowsPos2.setting.settingEnum.OperateStatus;
import jakarta.persistence.*;
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
public class Setting extends BaseEntity {

    //    영업 중단
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private OperatePause operatePause = new OperatePause();

    //    영업 시간
    @Setter
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private OperateTime operateTime = new OperateTime();

    //    조리 예상 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private EstimatedCookingTime estimatedCookingTime = new EstimatedCookingTime();

    //    도착 예상 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private EstimatedArrivalTime estimatedArrivalTime = new EstimatedArrivalTime();

    //    휴무일
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private ClosedDays closedDays = new ClosedDays();

    //    브레이크 타임
    @Setter
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private BreakTime breakTime = new BreakTime();

    //    영업 상태
    @Setter
    @Enumerated(EnumType.STRING)
    private OperateStatus operateStatus = OperateStatus.END;

    @OneToOne
    @JoinColumn(name = "member_id")
    @Setter
    private Member member;
}
