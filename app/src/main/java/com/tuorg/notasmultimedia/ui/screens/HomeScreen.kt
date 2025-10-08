package com.tuorg.notasmultimedia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuorg.notasmultimedia.model.ItemType
import com.tuorg.notasmultimedia.model.NoteItem
import com.tuorg.notasmultimedia.nav.Routes
import java.time.LocalDateTime
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController) {
    var items by remember { mutableStateOf(dummyItems()) }
    var query by remember { mutableStateOf("") }
    var tabIndex by remember { mutableStateOf(0) }

    // Filtrado simple
    val filtered = items.filter {
        it.title.contains(query, true) || it.description.contains(query, true)
    }.let {
        when (tabIndex) {
            1 -> it.filter { n -> n.type == ItemType.TASK }.sortedBy { it.dueAt }
            2 -> it.filter { n -> n.type == ItemType.NOTE }.sortedByDescending { it.createdAt }
            else -> it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notas & Tareas") },
                actions = {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Buscar…") },
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        singleLine = true,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .width(220.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { nav.navigate(Routes.EDIT) }) {
                Icon(Icons.Default.Add, contentDescription = "Nueva")
            }
        }
    ) { innerPadding ->

        Column(Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = tabIndex) {
                listOf("Todos", "Tareas", "Notas").forEachIndexed { i, label ->
                    Tab(
                        selected = tabIndex == i,
                        onClick = { tabIndex = i },
                        text = { Text(label) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(filtered) { note ->
                    NoteCard(note = note, onClick = { nav.navigate("detail/${note.id}") })
                }
            }
        }
    }
}

@Composable
fun NoteCard(note: NoteItem, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = if (note.type == ItemType.TASK) "Tarea: ${note.title}" else note.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.description.take(60),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (note.type == ItemType.TASK)
                    "Vence: ${note.dueAt}"
                else
                    "Creada: ${note.createdAt}",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

// Datos de prueba
private fun dummyItems(): List<NoteItem> {
    val now = LocalDateTime.now()
    return listOf(
        NoteItem(
            id = UUID.randomUUID().toString(),
            title = "Entregar reporte",
            description = "Resumen del avance y métricas.",
            type = ItemType.TASK,
            createdAt = now.minusDays(1),
            dueAt = now.plusDays(1)
        ),
        NoteItem(
            id = UUID.randomUUID().toString(),
            title = "Ideas de video",
            description = "Storyboard y locaciones.",
            type = ItemType.NOTE,
            createdAt = now.minusHours(5)
        )
    )
}
