/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sk.potociarm.workguard.data

import android.content.Context
import sk.potociarm.workguard.data.workevent.OfflineWorkEventsRepository
import sk.potociarm.workguard.data.workevent.WorkEventsRepository
import sk.potociarm.workguard.data.worktag.OfflineWorkTagsRepository
import sk.potociarm.workguard.data.worktag.WorkTagsRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val tagRepository: WorkTagsRepository
    val eventRepository: WorkEventsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val tagRepository: WorkTagsRepository by lazy {
        OfflineWorkTagsRepository(WorkGuardDatabase.getDatabase(context).workTagDao())
    }
    override val eventRepository: WorkEventsRepository by lazy {
        OfflineWorkEventsRepository(WorkGuardDatabase.getDatabase(context).workEventDao())
    }
}
