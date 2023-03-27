package com.jobis.refund.repository.salary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobis.refund.domain.User.entity.Salary;

public interface SalaryRepository extends JpaRepository<Salary, String>{
    List<Salary> findByRegNo(String regNo);
}
