package com.example.misis.UI.common.task

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.misis.UI.common.checkbox.CustomCheckbox
import com.example.misis.Core.ToDoItem.TaskImportance
import com.example.misis.Core.ToDoItem.TodoItem
import com.example.misis.UI.screens.convertMillisToDate
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun TaskContentUI(
    item: TodoItem,
    modifier: Modifier = Modifier,
    onComplete: (Boolean) -> Unit = {},
    onEdit: (TodoItem) -> Unit = {}
) {
    val showInfo = remember { mutableStateOf(false) }
    val textColor = getTextColor(item)
    val iconColor = getIconColor(item)
    val checkboxBorderColor = getCheckboxBorderColor(item)
    val lowArrow = item.importance == TaskImportance.LOW

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TaskCheckbox(item, onComplete, checkboxBorderColor)
        Spacer(modifier = Modifier.padding(8.dp))
        if (lowArrow) {
            LowImportanceArrowIcon()
        }

        TaskTextContent(item, textColor, iconColor)
        Spacer(modifier = Modifier.weight(1f))
        InfoButton(showInfo)
    }

    if (showInfo.value) {
        TaskInfoDialog(item, showInfo)
    }
}

@Composable
private fun getTextColor(item: TodoItem) =
    if (item.isDone) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onPrimary

@Composable
private fun getIconColor(item: TodoItem) =
    if (item.isDone) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondary

@Composable
private fun getCheckboxBorderColor(item: TodoItem) =
    when {
        item.isDone -> Color.Transparent
        item.importance == TaskImportance.HIGH -> MaterialTheme.colorScheme.surfaceContainerHigh
        else -> MaterialTheme.colorScheme.outlineVariant
    }

@Composable
private fun TaskCheckbox(item: TodoItem, onComplete: (Boolean) -> Unit, checkboxBorderColor: Color) {
    CustomCheckbox(
        isChecked = item.isDone,
        onCheckChanged = { onComplete(it) },
        modifier = Modifier
            .size(24.dp)
            .padding()
            .border(1.dp, checkboxBorderColor, CircleShape)
    )
}

@Composable
private fun LowImportanceArrowIcon() {
    Icon(
        imageVector = Icons.Default.ArrowDropDown,
        contentDescription = "",
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .size(20.dp)
    )
}

@Composable
private fun TaskTextContent(item: TodoItem, textColor: Color, iconColor: Color) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (item.importance == TaskImportance.HIGH) "❗ " + item.bodyText else item.bodyText,
                textAlign = TextAlign.Left,
                color = textColor,
                style = if (item.isDone) {
                    TextStyle(textDecoration = TextDecoration.LineThrough)
                } else {
                    TextStyle()
                },
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }

        item.deadline?.let { deadline ->
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Deadline",
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = convertMillisToDate(deadline.toEpochSecond() * 1000),
                    color = textColor,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@Composable
private fun InfoButton(showInfo: MutableState<Boolean>) {
    IconButton(onClick = { showInfo.value = true }, modifier = Modifier.fillMaxHeight()) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Info",
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun TaskInfoDialog(item: TodoItem, showInfo: MutableState<Boolean>) {
    AlertDialog(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(10.dp)
            ),
        containerColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = { showInfo.value = !showInfo.value },
        title = { Text(text = "Info", color = MaterialTheme.colorScheme.onSecondary) },
        text = {
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = screenHeight * 0.5f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = item.bodyText,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                item.deadline?.let { deadline ->
                    Text(
                        text = "Deadline: ${convertMillisToDate(deadline.toEpochSecond() * 1000)}",
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { showInfo.value = false }) {
                Text(
                    text = "Close",
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    )
}

@Composable
fun TaskUI(
    item: TodoItem,
    modifier: Modifier = Modifier,
    onComplete: (Boolean) -> Unit = {},
    onEdit: (TodoItem) -> Unit = {},
    onDelete: (String) -> Unit = {},
    onViewDetails: (TodoItem) -> Unit = {}
) {
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val maxOffset = 300f
    val thresholdDelete = -100f
    val thresholdButtons = 100f
    val thresholdComplete = 300f

    var showInfo = remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .padding(5.dp)
            .background(
                when {
                    offsetX.value < 0 -> MaterialTheme.colorScheme.surfaceContainerHigh
                    offsetX.value > 0 -> MaterialTheme.colorScheme.surface
                    else -> MaterialTheme.colorScheme.background
                }
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showInfo.value = true
                    }
                )
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {

                            when {
                                offsetX.value <= thresholdDelete -> {
                                    onDelete(item.id)
                                    offsetX.animateTo(0f)
                                }

                                offsetX.value >= thresholdComplete -> {
                                    onComplete(true)
                                    offsetX.animateTo(0f)
                                }

                                (offsetX.value > thresholdButtons && offsetX.value < thresholdComplete) -> {
                                    offsetX.animateTo(300f)
                                }

                                else -> {
                                    offsetX.animateTo(0f)
                                }
                            }
                        }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        coroutineScope.launch {
                            offsetX.snapTo(
                                (offsetX.value + dragAmount).coerceIn(-maxOffset, maxOffset)
                            )
                        }
                    }
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (offsetX.value >= thresholdButtons) {
                IconButton(onClick = { onEdit(item) }) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp),
                        imageVector = Icons.Default.Check,
                        contentDescription = "Complete",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            if (offsetX.value < 0) {
                IconButton(onClick = {  onDelete(item.id) }) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp),
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(5.dp)
                )
                .background(MaterialTheme.colorScheme.primary)
        ) {
            TaskContentUI(
                item = item,
                modifier,
                onComplete = onComplete,
                onEdit = onEdit
            )
        }
    }

    if (showInfo.value) {
        TaskInfoDialog(item, showInfo)
    }
}
