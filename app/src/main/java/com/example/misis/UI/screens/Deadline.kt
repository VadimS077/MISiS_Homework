package com.example.misis.UI.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Date


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yy").format(millis)
    return formatter.format(Date(millis))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlinePicker(
    initialDate: OffsetDateTime?,
    onDateSelected: (OffsetDateTime?) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate?.toEpochSecond()
    )

    val selectedDateString = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)

    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(color = MaterialTheme.colorScheme.primary),
            value = selectedDateString,
            onValueChange = {},
            readOnly = true,
            colors = TextFieldDefaults.colors(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.onSecondary,
                MaterialTheme.colorScheme.onPrimary,
                MaterialTheme.colorScheme.outlineVariant,
                MaterialTheme.colorScheme.outlineVariant
            ),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Выбрать дату"
                    )
                }
            },

            )
        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .padding(bottom = 16.dp)
                ) {

                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primary),
                        colors = DatePickerDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceTint,
                            dividerColor = MaterialTheme.colorScheme.outlineVariant,
                            dayContentColor = MaterialTheme.colorScheme.onSecondary,
                            todayContentColor = MaterialTheme.colorScheme.onPrimary,
                            todayDateBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                            selectedDayContentColor = MaterialTheme.colorScheme.primary,
                            selectedDayContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            selectedYearContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            yearContentColor = MaterialTheme.colorScheme.onSecondary,
                            disabledSelectedYearContainerColor = MaterialTheme.colorScheme.outlineVariant,
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text(
                                text = "Отмена",
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        TextButton(onClick = {
                            val selectedDate = datePickerState.selectedDateMillis?.let { OffsetDateTime.ofInstant(
                                Instant.ofEpochMilli(it), ZoneOffset.UTC) }
                            onDateSelected(selectedDate)
                            showDatePicker = false
                        }) {
                            Text(
                                text = "Выбрать",
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        }
    }
}