package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.ReunionBottomNavigationBar
import com.example.ui.components.ReunionTopAppBar
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.ReunionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: ReunionViewModel = viewModel()
                val currentScreen by viewModel.currentScreen.collectAsState()
                val selectedClassmate by viewModel.selectedClassmate.collectAsState()
                val showAddEditDialog by viewModel.showAddEditDialog.collectAsState()
                val classmateBeingEdited by viewModel.classmateBeingEdited.collectAsState()
                val showImportExportDialog by viewModel.showImportExportDialog.collectAsState()
                val showEventDialog by viewModel.showEventDialog.collectAsState()
                val eventBeingEdited by viewModel.eventBeingEdited.collectAsState()
                val snackbarMessage by viewModel.snackbarMessage.collectAsState()

                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(snackbarMessage) {
                    snackbarMessage?.let {
                        snackbarHostState.showSnackbar(it)
                        viewModel.clearSnackbar()
                    }
                }

                if (selectedClassmate != null) {
                    ClassmateDetailScreen(
                        classmate = selectedClassmate!!,
                        viewModel = viewModel
                    )
                } else {
                    Scaffold(
                        topBar = {
                            ReunionTopAppBar(
                                currentScreen = currentScreen,
                                onOpenGoogleForm = { viewModel.openGoogleForm(this@MainActivity) },
                                onOpenImportExport = { viewModel.openImportExportDialog() }
                            )
                        },
                        bottomBar = {
                            ReunionBottomNavigationBar(
                                currentScreen = currentScreen,
                                onScreenSelected = { viewModel.setScreen(it) }
                            )
                        },
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        val screenModifier = Modifier.padding(innerPadding)
                        when (currentScreen) {
                            AppScreen.HOME -> HomeScreen(viewModel = viewModel, modifier = screenModifier)
                            AppScreen.CLASSMATES -> ClassmatesListScreen(viewModel = viewModel, modifier = screenModifier)
                            AppScreen.EVENTS -> EventsScheduleScreen(viewModel = viewModel, modifier = screenModifier)
                            AppScreen.MEMORIES -> MemoriesAndGalleryScreen(viewModel = viewModel, modifier = screenModifier)
                        }
                    }
                }

                // Add or Edit Classmate Dialog (Google Form fields)
                if (showAddEditDialog) {
                    AddEditClassmateDialog(
                        classmate = classmateBeingEdited,
                        viewModel = viewModel,
                        onDismiss = { viewModel.dismissAddEditDialog() }
                    )
                }

                // Google Form Sync & CSV Dialog
                if (showImportExportDialog) {
                    GoogleFormSyncDialog(
                        viewModel = viewModel,
                        onDismiss = { viewModel.dismissImportExportDialog() }
                    )
                }

                // Add or Edit Event Dialog
                if (showEventDialog) {
                    AddEditEventDialog(
                        event = eventBeingEdited,
                        viewModel = viewModel,
                        onDismiss = { viewModel.dismissEventDialog() }
                    )
                }
            }
        }
    }
}
