package sk.potociarm.workguard.ui.events.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.potociarm.workguard.R
import sk.potociarm.workguard.ui.component.RowDescUiComponent
import sk.potociarm.workguard.ui.events.WorkEventState
import sk.potociarm.workguard.ui.events.sampleEventWithTag
import sk.potociarm.workguard.ui.events.sampleRunningEventWithTag
import sk.potociarm.workguard.ui.tags.WorkTagState
import sk.potociarm.workguard.ui.tags.component.WorkTagCard
import sk.potociarm.workguard.ui.tags.sampleTagUiWithParent
import sk.potociarm.workguard.ui.theme.WorkGuardTheme

