package com.github.olcmateusz.warglaive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.olcmateusz.warglaive.domain.Statistic;

public interface StatisticRepository extends JpaRepository<Statistic, Long>{

}
