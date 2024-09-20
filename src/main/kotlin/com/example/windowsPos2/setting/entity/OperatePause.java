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
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
@Entity
public class OperatePause extends BaseEntity {

    // 영업 일시 정지 시작 시간
    private LocalTime operatePauseStartTime = LocalTime.of(0, 0);

    // 영업 일시 정지 종료 시간
    private LocalTime operatePauseEndTime = LocalTime.of(0, 0);

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

    private OperatePause(Builder builder) {
        this.operatePauseStartTime = builder.operatePauseStartTime != null ? builder.operatePauseStartTime : this.operatePauseStartTime;
        this.operatePauseEndTime = builder.operatePauseEndTime != null ? builder.operatePauseEndTime : this.operatePauseEndTime;
        this.setting = builder.setting != null ? builder.setting : this.setting;
    }

    public static class Builder {
        private LocalTime operatePauseStartTime;
        private LocalTime operatePauseEndTime;
        private Setting setting;

        //        기본 생성자
        public Builder () {}

//        기존 객체를 기반으로하는 생성자 (빌더 패턴에서 부분 수정을 하기 위해 사용됨)
        public Builder (OperatePause operatePause) {
            this.operatePauseStartTime = operatePause.operatePauseStartTime;
            this.operatePauseEndTime = operatePause.operatePauseEndTime;
            this.setting = operatePause.setting;
        }

        public Builder operatePauseStartTime(LocalTime operatePauseStartTime) {
            this.operatePauseStartTime = operatePauseStartTime;
            return this;
        }

        public Builder operatePauseEndTime(LocalTime operatePauseEndTime) {
            this.operatePauseEndTime = operatePauseEndTime;
            return this;
        }

        public Builder setting(Setting setting) {
            this.setting = setting;
            return this;
        }

        public OperatePause build() {
            return new OperatePause(this);
        }
    }
}
