package com.jobis.refund.repository.deduction;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobis.refund.domain.User.entity.Deduction;
import com.jobis.refund.domain.User.entity.SzsUser;

public interface DeductionRepository extends JpaRepository<Deduction, String>{
    List<Deduction> findBySzsUser(SzsUser szsUser);

    Optional<Deduction> findByGubunAndSzsUser(String gubun, SzsUser szsUser);
}
