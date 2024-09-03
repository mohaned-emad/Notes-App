package com.mohaned.notesapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mohaned.notesapp.database.Note
import com.mohaned.notesapp.routes.Route
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mohaned.notesapp.ui.viewmodels.NoteViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = viewModel()
) {
    Scaffold (
        floatingActionButtonPosition = FabPosition.EndOverlay,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Route.ADD_NOTE) },
                containerColor = Color.Blue
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Adding note button",
                    tint = Color.White
                )
            }
        }
    ) {
        _ ->
        val notes by viewModel.getNotes().collectAsState(initial = emptyList())

        LazyColumn (
            modifier = modifier
                .padding(top = 64.dp)
        ) {
            items(notes) { note ->
                NoteListItem(note = note) {
                    if(note.title == "")
                        navController.navigate("${Route.EDIT_NOTE}/${note.id}/${note.details}")
                    else
                        navController.navigate("${Route.EDIT_NOTE}/${note.id}/${note.title}/${note.details}")
                }
            }
        }
    }
}

@Composable
fun NoteListItem(note: Note, modifier: Modifier = Modifier, onNavigate: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onNavigate() }
    ) {
        Column (
            modifier = modifier
                .padding(16.dp)
        ) {
            if(note.title != "")
                Text(
                    text = note.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Justify,
                    maxLines = 1,
                    modifier = modifier
                        .wrapContentHeight(Alignment.CenterVertically)
                )

            Text(
                text = note.details,
                fontSize = 20.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Justify,
                modifier = modifier
                    .defaultMinSize(minHeight = 80.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
            )

        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    val note = Note(1, "", "test 1")
    val note2 = Note(2, "Test", "test 2")
    LazyColumn {
        items(listOf(note, note2)) {
            NoteListItem(note = it, onNavigate = {})
        }
    }
}
