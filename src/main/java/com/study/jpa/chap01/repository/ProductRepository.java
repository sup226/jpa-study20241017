package com.study.jpa.chap01.repository;

import com.study.jpa.chap01.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속한 후 첫번째 제넥릭에 엔터티클래스의 타입(Product).
// 두번째에 PrimaryKey의 타입을 작성합니다(Long).
public interface ProductRepository extends JpaRepository<Product, Long> {
}
