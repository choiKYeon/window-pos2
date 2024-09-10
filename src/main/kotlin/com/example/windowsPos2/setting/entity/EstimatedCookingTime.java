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
public class EstimatedCookingTime extends BaseEntity {

    //    예상 조리시간 자동 설정
    private Boolean estimatedCookingTimeControl = false;

    //    예상 조리시간
    private Integer estimatedCookingTime = 0;

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

//    @Builder
    public EstimatedCookingTime (Boolean estimatedCookingTimeControl,
                                  Integer estimatedCookingTime,
                                  Setting setting) {
        this.estimatedCookingTimeControl = estimatedCookingTimeControl != null ? estimatedCookingTimeControl : this.estimatedCookingTimeControl;
        this.estimatedCookingTime = estimatedCookingTime != null ? estimatedCookingTime : this.estimatedCookingTime;
        this.setting = setting != null ? setting : this.setting;
    }

    public static class Builder {
        private Boolean estimatedCookingTimeControl;
        private Integer estimatedCookingTime;
        private Setting setting;

        public Builder estimatedCookingTimeControl(Boolean estimatedCookingTimeControl) {
            this.estimatedCookingTimeControl = estimatedCookingTimeControl;
            return this;
        }

        public Builder estimatedCookingTime(Integer estimatedCookingTime) {
            this.estimatedCookingTime = estimatedCookingTime;
            return this;
        }

        public Builder setting(Setting setting) {
            this.setting = setting;
            return this;
        }

        public EstimatedCookingTime build() {
            return new EstimatedCookingTime(estimatedCookingTimeControl, estimatedCookingTime, setting);
        }
    }
}
