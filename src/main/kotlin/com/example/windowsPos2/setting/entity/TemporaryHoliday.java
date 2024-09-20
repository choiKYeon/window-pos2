package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
@SuperBuilder
@Entity
public class TemporaryHoliday extends BaseEntity {

    //    임시 휴무 시작 날짜
    private LocalDate temporaryHolidayStartDate = LocalDate.now();

    //    임시 휴무 종료 날짜
    private LocalDate temporaryHolidayEndDate = LocalDate.now().plusDays(1);

    @ManyToOne
    @JoinColumn(name = "closed_days_id")
    @JsonBackReference
    private ClosedDays closedDays;

    private TemporaryHoliday(Builder builder) {
        this.temporaryHolidayStartDate = builder.temporaryHolidayStartDate != null ? builder.temporaryHolidayStartDate : this.temporaryHolidayStartDate;
        this.temporaryHolidayEndDate = builder.temporaryHolidayEndDate != null ? builder.temporaryHolidayEndDate : this.temporaryHolidayEndDate;
        this.closedDays = builder.closedDays != null ? builder.closedDays : this.closedDays;
    }

    public static class Builder {
        private LocalDate temporaryHolidayStartDate;
        private LocalDate temporaryHolidayEndDate;
        private ClosedDays closedDays;

        public Builder temporaryHolidayStartDate(LocalDate temporaryHolidayStartDate) {
            this.temporaryHolidayStartDate = temporaryHolidayStartDate;
            return this;
        }

        public Builder temporaryHolidayEndDate(LocalDate temporaryHolidayEndDate) {
            this.temporaryHolidayEndDate = temporaryHolidayEndDate;
            return this;
        }

        public Builder closedDays(ClosedDays closedDays) {
            this.closedDays = closedDays;
            return this;
        }

        public TemporaryHoliday build() {
            return new TemporaryHoliday(this);
        }
    }
}
