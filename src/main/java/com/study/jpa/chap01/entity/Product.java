package com.study.jpa.chap01.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter @ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 테이블과 1대1로 매칭되는 클래스
@Entity
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    @Column(name = "prod_id")
    private Long id;

    @Column(name = "prod_nm", length = 30, nullable = false)
    private String name;

    @Column(name = "prod_price")
    private int price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @CreationTimestamp // INSERT 시에 자동으로 서버시간 저장
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // UPDATE문 실행 시 자동으로 시간이 저장
    private LocalDateTime updateAt;

    // 데이터베이스에는 저장 안하고 클래스 내부에서만 사용할 필드
    @Transient
    private String nickname;


    public enum Category {
        FOOD, FASHION, ELECTRONIC
    }

}
