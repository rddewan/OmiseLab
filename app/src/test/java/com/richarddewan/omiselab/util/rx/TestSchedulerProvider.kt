package com.richarddewan.omiselab.util.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val testScheduler: TestScheduler): ScheduleProvider {

    override fun computation(): Scheduler = testScheduler

    override fun io(): Scheduler = testScheduler

    override fun ui(): Scheduler = testScheduler
}