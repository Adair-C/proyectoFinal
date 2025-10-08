package com.tuorg.notasmultimedia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuorg.notasmultimedia.model.ItemType
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(nav: NavController) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(ItemType.TASK) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar") },
                navigationIcon = { TextButton(onClick = { nav.popBackStack() }) { Text("Cancelar") } },
                actions = { TextButton(onClick = { nav.popBackStack() }) { Text("Guardar") } } // prototipo
            )
        }
    ) { pads ->
        Column(Modifier.padding(pads).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Descripción") }, minLines = 3, modifier = Modifier.fillMaxWidth())

            Text("Tipo")
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FilterChip(selected = type == ItemType.TASK, onClick = { type = ItemType.TASK }, label = { Text("Tarea") })
                FilterChip(selected = type == ItemType.NOTE, onClick = { type = ItemType.NOTE }, label = { Text("Nota") })
            }

            // Placeholders (sin lógica real aún)
            ElevatedButton(onClick = { /* TODO: fecha/hora */ }) { Text("Elegir fecha/hora (si Tarea)") }
            ElevatedButton(onClick = { /* TODO: añadir recordatorio */ }) { Text("Añadir recordatorio") }

            Text("Adjuntos")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { /* TODO: foto */ }) { Text("+ Foto") }
                OutlinedButton(onClick = { /* TODO: video */ }) { Text("+ Video") }
                OutlinedButton(onClick = { /* TODO: archivo */ }) { Text("+ Archivo") }
                OutlinedButton(onClick = { /* TODO: audio */ }) { Text("+ Audio") }
            }
        }
    }
}
