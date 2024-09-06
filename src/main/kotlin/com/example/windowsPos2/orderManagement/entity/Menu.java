package com.example.windowsPos2.orderManagement.entity;

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
public class Menu extends BaseEntity {

    //    메뉴 이름
    private String menuName;

    //    메뉴 가격
    private Long menuPrice;

    //    메뉴 수량
    private Integer menuCount;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Setter
    private List<MenuOption> menuOptions = new ArrayList<>();

    //    @JsonBackReference는 순환참조 에러를 해결하기 위한 방법
    @ManyToOne
    @JoinColumn(name = "order_management_id")
    @JsonBackReference
    private OrderManagement orderManagement;
}
