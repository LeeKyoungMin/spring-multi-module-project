package com.jobis.refund.repository.taxAmount;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobis.refund.domain.User.entity.SzsUser;
import com.jobis.refund.domain.User.entity.TaxAmount;

public interface TaxAmountRepository extends JpaRepository<TaxAmount, String>{
    Optional<TaxAmount> findBySzsUser(SzsUser szsUser);
}
