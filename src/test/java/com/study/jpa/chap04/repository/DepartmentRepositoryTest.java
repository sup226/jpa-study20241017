package com.study.jpa.chap04.repository;

import com.study.jpa.chap04.entity.Department;
import com.study.jpa.chap04.entity.Employee;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class DepartmentRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    @DisplayName("부서 정보 조회하면 해당 부서원들도 함께 조회되어야 한다.")
    void testFindDept() {
        // given
        Long id = 1L;

        // when
        Department department = departmentRepository.findById(id).orElseThrow();

        // then
        System.out.println("\n\n\n");
        System.out.println("department = " + department);
        System.out.println("department = " + department.getEmployees() );
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("N+1 문제 발생 예시")
    void testNPlusOneEx() {
        // given
        List<Department> departments = departmentRepository.findAll();

        // when
        departments.forEach(dept -> {
            System.out.println("======= 사원 리스트 =======");
            List<Employee> empList = dept.getEmployees();
            System.out.println(empList);

            System.out.println("\n\n");
        });

        // then
    }

    @Test
    @DisplayName("N+1 문제 해결")
    void testNPlusOneSol() {
        // given
        List<Department> departments = departmentRepository.findAllIncludesEmployees();

        // when
        departments.forEach(dept -> {
            System.out.println("======= 사원 리스트 =======");
            List<Employee> empList = dept.getEmployees();
            System.out.println(empList);

            System.out.println("\n\n");
        });

        // then
    }
    
    @Test
    @DisplayName("고아 객체 삭제")
    void orphanRemovalTest() {
        // given
        Department department = departmentRepository.findById(2L).orElseThrow();

        // 2번 부서 사원 목록 가져오기
        List<Employee> employeeList = department.getEmployees();

        // when

        Employee employee = employeeList.get(1);
        employeeList.remove(employee);

        // then
    }

    @Test
    @DisplayName("양방향 관계에서 CascadeType을 PERSIST를 주면 부모가 데이터 변경의 주체가 된다.")
    void cascadePersistTest() {
        // given
        Department department = departmentRepository.findById(2L).orElseThrow();

        Employee pororo = Employee.builder()
                .name("뽀로로")
                .department(department) // 속할 부서를 전달해줘야 합니다.
                .build();

        // when
        department.getEmployees().add(pororo);

        // then
    }

}