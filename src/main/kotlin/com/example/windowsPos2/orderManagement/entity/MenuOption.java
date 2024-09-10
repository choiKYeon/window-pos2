package com.example.windowsPos2.orderManagement.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
@Entity
public class MenuOption extends BaseEntity {

    //    옵션 이름
    private String optionName;

    //    옵션 가격
    private Long optionPrice;

    @Setter
    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    private Menu menu;

//    @Builder
    public MenuOption(String optionName, Long optionPrice, Menu menu) {
        this.optionName = optionName != null ? optionName : this.optionName;
        this.optionPrice = optionPrice != null ? optionPrice : this.optionPrice;
        this.menu = menu != null ? menu : this.menu;
    }

    public static class Builder {
        private String optionName;
        private Long optionPrice;
        private Menu menu;

        public Builder optionName(String optionName) {
            this.optionName = optionName;
            return this;
        }

        public Builder optionPrice(Long optionPrice) {
            this.optionPrice = optionPrice;
            return this;
        }

        public Builder menu(Menu menu) {
            this.menu = menu;
            return this;
        }

        public MenuOption build() {
            return new MenuOption(optionName, optionPrice, menu);
        }
    }
}
