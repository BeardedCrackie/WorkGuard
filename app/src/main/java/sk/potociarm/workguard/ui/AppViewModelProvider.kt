
package sk.potociarm.workguard.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import sk.potociarm.workguard.WorkGuardApplication
import sk.potociarm.workguard.ui.tags.WorkTagDetailsViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire WorkGuard app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            WorkTagDetailsViewModel(
                this.createSavedStateHandle()
            )
        }
    }
}


/**
 * Extension function to queries for [Application] object and returns an instance of
 * [WorkGuardApplication].
 */
fun CreationExtras.inventoryApplication(): WorkGuardApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as WorkGuardApplication)
