package com.study.jpa.chap04.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString(exclude = {"department"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id; // 사원번호

    @Column(name = "emp_name", nullable = false)
    private String name; // 사원명

    @ManyToOne
    @JoinColumn(name = "dept_id") // FK 컬럼명 (연관 테이블의 컬럼명과 일치하게 작성)
    private Department department;

    // 연관관계 편의 메서드 (양방향에서 연관 필드가 수정될 경우
    // 실제 테이블과의 데이터를 맞춰주기 위한 메서드 선언
    public void changeDepartment(Department department) {
        this.department = department;
        department.getEmployees().add(this);
    }

}
