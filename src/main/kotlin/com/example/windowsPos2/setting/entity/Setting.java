package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.example.windowsPos2.member.entity.Member;
import com.example.windowsPos2.setting.settingEnum.OperateStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
@Entity
public class Setting extends BaseEntity {

    // 영업 중단
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private OperatePause operatePause = new OperatePause();

    // 영업 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private OperateTime operateTime = new OperateTime();

    // 조리 예상 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private EstimatedCookingTime estimatedCookingTime = new EstimatedCookingTime();

    // 도착 예상 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private EstimatedArrivalTime estimatedArrivalTime = new EstimatedArrivalTime();

    // 휴무일
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private ClosedDays closedDays = new ClosedDays();

    // 브레이크 타임
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private BreakTime breakTime = new BreakTime();

    // 영업 상태
    @Enumerated(EnumType.STRING)
    private OperateStatus operateStatus = OperateStatus.START;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Setting(OperatePause operatePause, OperateTime operateTime, EstimatedCookingTime estimatedCookingTime,
                   EstimatedArrivalTime estimatedArrivalTime, ClosedDays closedDays, BreakTime breakTime,
                   OperateStatus operateStatus, Member member) {

        this.operatePause = operatePause != null ? operatePause : this.operatePause;
        this.operateTime = operateTime != null ? operateTime : this.operateTime;
        this.estimatedCookingTime = estimatedCookingTime != null ? estimatedCookingTime : this.estimatedCookingTime;
        this.estimatedArrivalTime = estimatedArrivalTime != null ? estimatedArrivalTime : this.estimatedArrivalTime;
        this.closedDays = closedDays != null ? closedDays : this.closedDays;
        this.breakTime = breakTime != null ? breakTime : this.breakTime;
        this.operateStatus = operateStatus != null ? operateStatus : this.operateStatus;
        this.member = member != null ? member : this.member;
    }

    public static class Builder {
        private OperatePause operatePause;
        private OperateTime operateTime;
        private EstimatedCookingTime estimatedCookingTime;
        private EstimatedArrivalTime estimatedArrivalTime;
        private ClosedDays closedDays;
        private BreakTime breakTime;
        private OperateStatus operateStatus;
        private Member member;

        public Builder operatePause(OperatePause operatePause) {
            this.operatePause = operatePause;
            return this;
        }

        public Builder operateTime(OperateTime operateTime) {
            this.operateTime = operateTime;
            return this;
        }

        public Builder estimatedCookingTime(EstimatedCookingTime estimatedCookingTime) {
            this.estimatedCookingTime = estimatedCookingTime;
            return this;
        }

        public Builder estimatedArrivalTime(EstimatedArrivalTime estimatedArrivalTime) {
            this.estimatedArrivalTime = estimatedArrivalTime;
            return this;
        }

        public Builder closedDays(ClosedDays closedDays) {
            this.closedDays = closedDays;
            return this;
        }

        public Builder breakTime(BreakTime breakTime) {
            this.breakTime = breakTime;
            return this;
        }

        public Builder operateStatus(OperateStatus operateStatus) {
            this.operateStatus = operateStatus;
            return this;
        }

        public Builder member(Member member) {
            this.member = member;
            return this;
        }

        public Setting build() {
            return new Setting(operatePause, operateTime, estimatedCookingTime, estimatedArrivalTime, closedDays, breakTime, operateStatus, member);
        }
    }
}
