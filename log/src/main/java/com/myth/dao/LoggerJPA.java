package com.myth.dao;

import com.myth.domain.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerJPA extends JpaRepository<LoggerEntity,Long>{
}
