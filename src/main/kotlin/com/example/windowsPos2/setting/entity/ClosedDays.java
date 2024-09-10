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
    public ClosedDays (List<TemporaryHoliday> temporaryHolidayList,
                       List<RegularHoliday> regularHolidayList,
                       Setting setting) {
        this.temporaryHolidayList = temporaryHolidayList != null ? temporaryHolidayList : this.temporaryHolidayList;
        this.regularHolidayList = regularHolidayList != null ? regularHolidayList : this.regularHolidayList;
        this.setting = setting != null ? setting : this.setting;
    }

    public static class Builder {
        private List<TemporaryHoliday> temporaryHolidayList;
        private List<RegularHoliday> regularHolidayList;
        private Setting setting;

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
            return new ClosedDays(temporaryHolidayList, regularHolidayList, setting);
        }
    }
}
