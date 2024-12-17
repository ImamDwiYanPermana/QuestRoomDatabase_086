package com.example.myapplication.ui.theme.viewMahasiswa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.Viewmodel.PenyediaViewModel
import com.example.myapplication.ui.theme.Viewmodel.updateMhsViewModel
import com.example.myapplication.ui.theme.costumwidget.TopAppbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMhsView(
    onBack : () -> Unit,
    onNavigate : () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: updateMhsViewModel = viewModel (factory = PenyediaViewModel.Factory) //Initialize ViewModel
) {
    val uiState = viewModel.updateUIState // ambil UI state  dari viewModel
    val snackbarHostState = remember { SnackbarHostState() } // snackbar state
    val coroutineScope = rememberCoroutineScope()

    //observe perubahan snackbarMessage
    LaunchedEffect(uiState.snackbarMessage) {
        println("LaunchedEffect triggered")
        uiState.snackbarMessage?.let{ message ->
            println("Launching coroutine for snackbar")
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long,
            )
            viewModel.resetSnackBarMessage()
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}, //tempatkan snackbar di scaffold
        topBar = {
            TopAppbar(
                judul = "Edit Mahasiswa",
                showBackButton = true,
                onBack = onBack,
            )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            //isi body
            InsertBodyMhs(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate()
                            }
                        }
                    }
                }
            )
        }
    }
}