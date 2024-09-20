package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
@Entity
public class ClosedDays extends BaseEntity {

    //    임시 휴무일
    @OneToMany(mappedBy = "closedDays", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<TemporaryHoliday> temporaryHolidayList = new ArrayList<>();

    //    정기 휴무일
    @OneToMany(mappedBy = "closedDays", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RegularHoliday> regularHolidayList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

//    @Builder
    private ClosedDays (Builder builder) {
        this.temporaryHolidayList = builder.temporaryHolidayList != null ? builder.temporaryHolidayList : this.temporaryHolidayList;
        this.regularHolidayList = builder.regularHolidayList != null ? builder.regularHolidayList : this.regularHolidayList;
        this.setting = builder.setting != null ? builder.setting : this.setting;
    }

    public static class Builder {
        private List<TemporaryHoliday> temporaryHolidayList;
        private List<RegularHoliday> regularHolidayList;
        private Setting setting;

        //        기본 생성자
        public Builder () {}

//        기존 객체를 기반으로하는 생성자 (빌더 패턴에서 부분 수정을 하기 위해 사용됨)
        public Builder (ClosedDays closedDays) {
            this.temporaryHolidayList = closedDays.temporaryHolidayList;
            this.regularHolidayList = closedDays.regularHolidayList;
            this.setting = closedDays.setting;
        }

        public Builder temporaryHolidayList(List<TemporaryHoliday> temporaryHolidayList) {
            this.temporaryHolidayList = temporaryHolidayList;
            return this;
        }

        public Builder regularHolidayList(List<RegularHoliday> regularHolidayList) {
            this.regularHolidayList = regularHolidayList;
            return this;
        }

        public Builder setting(Setting setting) {
            this.setting = setting;
            return this;
        }

        public ClosedDays build() {
            return new ClosedDays(this);
        }
    }
}
