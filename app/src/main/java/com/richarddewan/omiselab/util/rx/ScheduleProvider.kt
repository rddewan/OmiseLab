package com.richarddewan.omiselab.util.rx

import io.reactivex.Scheduler
import javax.inject.Singleton


@Singleton
interface ScheduleProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}