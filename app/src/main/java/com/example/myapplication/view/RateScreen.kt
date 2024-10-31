package com.example.myapplication.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.utils.InputValidator
import com.example.myapplication.viewmodel.MainViewModel

@Composable
fun RateScreen(mainViewModel: MainViewModel = viewModel()) {
    var isLoading by remember { mutableStateOf(true) }
    val ratesFetched = remember { mutableStateOf(false) }
    val showErrorDialog by mainViewModel.networkError
    LaunchedEffect(Unit) {
        if (!ratesFetched.value) {
            mainViewModel.fetchRates()
            ratesFetched.value = true
            isLoading = false
        }
    }
    val rates by mainViewModel.rates.collectAsState()
    var amount by remember { mutableStateOf("") }
    var convertedAmount by remember { mutableStateOf(0.0) }
    var fromRate by remember { mutableStateOf<Pair<String, Double>?>(null) }
    var toRate by remember { mutableStateOf<Pair<String, Double>?>(null) }
    var amountError by remember { mutableStateOf(false) }
    var fromRateError by remember { mutableStateOf(false) }
    var toRateError by remember { mutableStateOf(false) }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { mainViewModel.networkError.value = false },
            confirmButton = {
                TextButton(onClick = { mainViewModel.networkError.value = false }) {
                    Text("OK")
                }
            },
            title = { Text("Network Error") },
            text = { Text("Failed to fetch data. Please check your network connection.") }
        )
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(26.dp)
            ) {
                Text(
                    text = "Exchange Rates",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        else{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                RateDropdown(
                    selectedRate = fromRate,
                    onRateSelected = { fromRate = it },
                    name = "From Rate",
                    rates = rates
                )
                if (fromRateError) {
                    Text("Please select a from rate", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(16.dp))
                RateDropdown(
                    selectedRate = toRate,
                    onRateSelected = { toRate = it },
                    name = "To Rate",
                    rates = rates
                )
                if (toRateError) {
                    Text("Please select a to rate", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Input Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = amountError
                )
                if (amountError) {
                    Text("Please enter an amount", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val isAmountValid = InputValidator.validateTextField(amount)
                    val isFromRateValid = InputValidator.validateDropdown(fromRate)
                    val isToRateValid = InputValidator.validateDropdown(toRate)

                    amountError = !isAmountValid
                    fromRateError = !isFromRateValid
                    toRateError = !isToRateValid

                    if (isAmountValid && isFromRateValid && isToRateValid) {
                        val fromRateValue = fromRate?.second ?: 1.0
                        val toRateValue = toRate?.second ?: 1.0
                        convertedAmount = (amount.toDoubleOrNull() ?: 0.0) * (toRateValue / fromRateValue)
                    }
                }) {
                    Text("Convert")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Converted Amount: $convertedAmount",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateDropdown(
    selectedRate: Pair<String, Double>?,
    onRateSelected: (Pair<String, Double>?) -> Unit,
    name : String,
    rates : Map<String, Double>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedRate?.first ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(name) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            rates.entries.forEach { rateEntry ->
                DropdownMenuItem(
                    text = { Text(rateEntry.key) },
                    onClick = {
                        onRateSelected(Pair(rateEntry.key, rateEntry.value))
                        expanded = false
                    }
                )
            }
        }
    }
}
@Preview
@Composable
fun PreviewRateScreen() {
    RateScreen()
}