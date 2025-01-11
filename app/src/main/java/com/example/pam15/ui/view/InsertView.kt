package com.example.pam15.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam15.ui.viemodel.FormErrorState
import com.example.pam15.ui.viemodel.FormState
import com.example.pam15.ui.viemodel.InsertUiState
import com.example.pam15.ui.viemodel.InsertViewModel
import com.example.pam15.ui.viemodel.MahasiswaEvent
import com.example.pam15.ui.viemodel.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }// State utama untuk loading, success, error
    val uiEvent = viewModel.uiEvent // State untuk form dan validasi val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

// Observasi perubahan state untuk snackbar dan navigasi
    LaunchedEffect(uiState) {
        when (uiState) {
            is FormState.Success -> {
                println(
                "InsertMhsView: uiState is FormState.Success, navigate to home " + uiState.message
            )
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message) // Tampilkan snackbar
                }
                delay(700)
                onNavigate()
                viewModel.resetSnackBarMessage() // Reset snackbar state
            }

            is FormState.Error -> {
                coroutineScope.launch {
                snackbarHostState.showSnackbar(uiState.message) }
            }
            else -> Unit
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Tambah Mahasiswa") }, navigationIcon = {
                    Button(onClick = onBack) { Text("Back")
                    }
                }
            )
        }
    ) { padding -> Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ) {

    }


@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier, onValueChange: (MahasiswaEvent) -> Unit, uiState: InsertUiState,
    onClick: () -> Unit, homeUiState: FormState
) {
    Column(
        modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMahasiswa(
            mahasiswaEvent = uiState.insertUiEvent, onValueChange = onValueChange, errorState = uiState.isEntryValid, modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = homeUiState !is FormState.Loading,
        ) {
            if (homeUiState is FormState.Loading) { CircularProgressIndicator(
                color = Color.White,

                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp)
            )
                Text("Loading...")
            } else {
                Text("Add")
            }
        }

    }
}








