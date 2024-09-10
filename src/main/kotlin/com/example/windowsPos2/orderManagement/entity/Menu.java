package com.example.windowsPos2.orderManagement.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
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
    @Setter
    @ManyToOne
    @JoinColumn(name = "order_management_id")
    @JsonBackReference
    private OrderManagement orderManagement;

    public Menu(String menuName, Long menuPrice, Integer menuCount, List<MenuOption> menuOptions, OrderManagement orderManagement) {
        this.menuName = menuName != null ? menuName : this.menuName;
        this.menuPrice = menuPrice != null ? menuPrice : this.menuPrice;
        this.menuCount = menuCount != null ? menuCount : this.menuCount;
        this.menuOptions = menuOptions != null ? menuOptions : new ArrayList<>(); // 메뉴 옵션이 null이면 빈 리스트로 설정
        this.orderManagement = orderManagement != null ? orderManagement : this.orderManagement;
    }

    public static class Builder {
        private String menuName;
        private Long menuPrice;
        private Integer menuCount;
        private List<MenuOption> menuOptions;
        private OrderManagement orderManagement;

        public Builder menuName(String menuName) {
            this.menuName = menuName;
            return this;
        }

        public Builder menuPrice(Long menuPrice) {
            this.menuPrice = menuPrice;
            return this;
        }

        public Builder menuCount(Integer menuCount) {
            this.menuCount = menuCount;
            return this;
        }

        public Builder menuOptions(List<MenuOption> menuOptions) {
            this.menuOptions = menuOptions;
            return this;
        }

        public Builder orderManagement(OrderManagement orderManagement) {
            this.orderManagement = orderManagement;
            return this;
        }

        public Menu build() {
            return new Menu(menuName, menuPrice, menuCount, menuOptions, orderManagement);
        }
    }
}
