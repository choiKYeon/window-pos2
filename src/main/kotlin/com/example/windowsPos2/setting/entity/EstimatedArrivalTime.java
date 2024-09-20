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
    private EstimatedArrivalTime (Builder builder) {
        this.estimatedArrivalTimeControl = builder.estimatedArrivalTimeControl != null ? builder.estimatedArrivalTimeControl : this.estimatedArrivalTimeControl;
        this.estimatedArrivalTime = builder.estimatedArrivalTime != null ? builder.estimatedArrivalTime : this.estimatedArrivalTime;
        this.setting = builder.setting != null ? builder.setting : this.setting;
    }

    public static class Builder {
        private Boolean estimatedArrivalTimeControl;
        private Integer estimatedArrivalTime;
        private Setting setting;

        //        기본 생성자
        public Builder () {}

//        기존 객체를 기반으로하는 생성자 (빌더 패턴에서 부분 수정을 하기 위해 사용됨)
        public Builder (EstimatedArrivalTime estimatedArrivalTime) {
            this.estimatedArrivalTimeControl = estimatedArrivalTime.estimatedArrivalTimeControl;
            this.estimatedArrivalTime = estimatedArrivalTime.estimatedArrivalTime;
            this.setting = estimatedArrivalTime.setting;
        }

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
            return new EstimatedArrivalTime(this);
        }
    }
}
