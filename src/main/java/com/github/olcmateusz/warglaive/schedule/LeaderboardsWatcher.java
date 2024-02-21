package com.github.olcmateusz.warglaive.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LeaderboardsWatcher {
	
	/*
	 * In order, * means 'for every'
	 * Seconds
	 * Minutes
	 * Hour
	 * Day of the month
	 * Month
	 * Day of the week
	 * further read: https://crontab.guru/ https://www.freeformatter.com/cron-expression-generator-quartz.html
	 */
	// every 3 hours: cron = "0 0 */3 * * *"
	@Scheduled(cron = "5 * * * * *")
	public void printSth() {
		System.out.println("test123");
	}

}
