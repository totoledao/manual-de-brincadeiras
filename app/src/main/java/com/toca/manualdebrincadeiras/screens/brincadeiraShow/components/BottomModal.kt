package com.toca.manualdebrincadeiras.screens.brincadeiraShow.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.toca.manualdebrincadeiras.database.Definicao


@Composable
fun BottomModal(
    showBottomModal: Boolean,
    definicao: Definicao,
    onDismiss: () -> Unit
): Unit? {
    return if (!showBottomModal) null
    else BotModal(definicao, onDismiss)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BotModal(definicao: Definicao, onDismiss: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = rememberModalBottomSheetState(),
        dragHandle = { BottomSheetDefaults.DragHandle() },
    )
    {
        Column(
            modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 48.dp),
        ) {
            Text(text = definicao.nome, style = MaterialTheme.typography.titleLarge)
            Text(text = definicao.descricao)
        }
    }
}