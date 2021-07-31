package com.code4copy.example.core.dao.api;

import com.code4copy.example.core.dao.domain.TotpDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToptRepository extends JpaRepository<TotpDO, String> {
}
