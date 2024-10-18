package com.study.jpa.chap04.repository;

import com.study.jpa.chap04.entity.Department;
import com.study.jpa.chap04.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EntityManager em;

    @Test
    void beforeInsert() {
        Department d1 = Department.builder()
                .name("영업부")
                .build();
        Department d2 = Department.builder()
                .name("개발부")
                .build();
        departmentRepository.save(d1);
        departmentRepository.save(d2);
        Employee e1 = Employee.builder()
                .name("라이옹")
                .department(d1)
                .build();
        Employee e2 = Employee.builder()
                .name("어피치")
                .department(d1)
                .build();
        Employee e3 = Employee.builder()
                .name("프로도")
                .department(d2)
                .build();
        Employee e4 = Employee.builder()
                .name("네오")
                .department(d2)
                .build();
        employeeRepository.save(e1);
        employeeRepository.save(e2);
        employeeRepository.save(e3);
        employeeRepository.save(e4);
    }

    @Test
    @DisplayName("부서 바꾸기")
    void testChangeDept() {
        // given
        Employee foundEmp =  employeeRepository.findById(1L).orElseThrow();

        Department newDept = departmentRepository.findById(2L).orElseThrow();

        foundEmp.setDepartment(newDept);

        // 연관관계 편의 메서드 호출 -> 데이터 수정시에는 반대편 엔터티도 꼭 수정을 해주자!
        foundEmp.changeDepartment(newDept);

//        employeeRepository.save(foundEmp);
//        newDept.getEmployees().add(foundEmp);

        /*
        em.flush(); // DB로 밀어내기
        em.clear(); // 영속성 컨텍스트 비우기(비우지 않으면 컨텍스트 내의 정보를 참조하려 하니까)
         */

        // when

//        Department foundDept = departmentRepository.findById(2L).orElseThrow();

        // then
        System.out.println("\n\n\n");
        newDept.getEmployees().forEach(emp -> System.out.println(emp));
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("사원 찾기")
    void testFindOne() {
        // given
        Long id = 2L;

        // when
        Employee employee = employeeRepository.findById(id).orElseThrow();

        // then
        System.out.println("\n\n\n");
        System.out.println("employee = " + employee);
        System.out.println("\n\n\n");

        assertEquals("어피치", employee.getName());
    }

}