package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
@Entity
public class EstimatedArrivalTime extends BaseEntity {

    //    예상 도착시간 자동 설정
    private Boolean estimatedArrivalTimeControl= false;

    //    예상 도착시간
    private Integer estimatedArrivalTime = 0;

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

//    @Builder
    public EstimatedArrivalTime (Boolean estimatedArrivalTimeControl,
                                  Integer estimatedArrivalTime,
                                  Setting setting) {
        this.estimatedArrivalTimeControl = estimatedArrivalTimeControl != null ? estimatedArrivalTimeControl : this.estimatedArrivalTimeControl;
        this.estimatedArrivalTime = estimatedArrivalTime != null ? estimatedArrivalTime : this.estimatedArrivalTime;
        this.setting = setting != null ? setting : this.setting;
    }

    public static class Builder {
        private Boolean estimatedArrivalTimeControl;
        private Integer estimatedArrivalTime;
        private Setting setting;

        public Builder estimatedArrivalTimeControl(Boolean estimatedArrivalTimeControl) {
            this.estimatedArrivalTimeControl = estimatedArrivalTimeControl;
            return this;
        }

        public Builder estimatedArrivalTime(Integer estimatedArrivalTime) {
            this.estimatedArrivalTime = estimatedArrivalTime;
            return this;
        }

        public Builder setting(Setting setting) {
            this.setting = setting;
            return this;
        }

        public EstimatedArrivalTime build() {
            return new EstimatedArrivalTime(estimatedArrivalTimeControl, estimatedArrivalTime, setting);
        }
    }
}
