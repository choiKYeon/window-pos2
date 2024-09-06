package com.example.windowsPos2.setting.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class ClosedDays extends BaseEntity {

    //    임시 휴무일
    @OneToMany(mappedBy = "closedDays", cascade = CascadeType.ALL)
    @JsonBackReference
    @Setter
    private List<TemporaryHoliday> temporaryHolidayList = new ArrayList<>();

    //    정기 휴무일
    @OneToMany(mappedBy = "closedDays", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Setter
    private List<RegularHoliday> regularHolidayList = new ArrayList<>();

    @Setter
    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}
