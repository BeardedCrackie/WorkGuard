
package sk.potociarm.workguard.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import sk.potociarm.workguard.WorkGuardApplication
import sk.potociarm.workguard.ui.events.WorkEventDetailsViewModel
import sk.potociarm.workguard.ui.events.WorkEventEditViewModel
import sk.potociarm.workguard.ui.events.WorkEventEntryViewModel
import sk.potociarm.workguard.ui.events.WorkEventListViewModel
import sk.potociarm.workguard.ui.tags.WorkTagDetailsViewModel
import sk.potociarm.workguard.ui.tags.WorkTagEditViewModel
import sk.potociarm.workguard.ui.tags.WorkTagEntryViewModel
import sk.potociarm.workguard.ui.tags.WorkTagListViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire WorkGuard app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            WorkTagDetailsViewModel(
                this.createSavedStateHandle(),
                workGuardApplication().container.tagRepository
            )
        }
        initializer {
            WorkTagEditViewModel(
                this.createSavedStateHandle(),
                workGuardApplication().container.tagRepository,
                workGuardApplication().container.eventRepository
            )
        }
        initializer {
            WorkTagEntryViewModel(
                workGuardApplication().container.tagRepository)
        }
        initializer {
            WorkTagListViewModel(
                workGuardApplication().container.tagRepository)
        }

        initializer {
            WorkEventDetailsViewModel(
                this.createSavedStateHandle(),
                workGuardApplication().container.eventRepository
            )
        }

        initializer {
            WorkEventListViewModel(
                workGuardApplication().container.tagRepository,
                workGuardApplication().container.eventRepository)
        }

        initializer {
            WorkEventEditViewModel(
                this.createSavedStateHandle(),
                workGuardApplication().container.tagRepository,
                workGuardApplication().container.eventRepository)
        }

        initializer {
            WorkEventEntryViewModel(
                workGuardApplication().container.tagRepository,
                workGuardApplication().container.eventRepository)
        }

    }
}


/**
 * Extension function to queries for [Application] object and returns an instance of
 * [WorkGuardApplication].
 */
fun CreationExtras.workGuardApplication(): WorkGuardApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as WorkGuardApplication)
