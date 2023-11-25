package com.github.olcmateusz.warglaive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.Statistic;
import com.github.olcmateusz.warglaive.repository.StatisticRepository;

@Service
public class StatisticService {

	@Autowired
	private StatisticRepository statisticRepo;
	
	public void save(Statistic statistic) {
		statisticRepo.save(statistic);
	}
}
