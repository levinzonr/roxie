package cz.levinzonr.roxie.sample.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import cz.levinzonr.roxie.sample.domain.Note

@Composable
fun NotesScreen(viewModel: NoteListViewModel) {
    val state = viewModel.stateFlow.collectAsState(initial = State()).value
    Scaffold(
        floatingActionButton = { FloatingActionButton(onClick = {
            viewModel.dispatch(Action.AddNote("adasdas"))
        }) {

        }}
    ) {
        NotesList(state.notes) { viewModel.dispatch(Action.DeleteNote(it)) }
    }
}

@Composable
private fun NotesList(list: List<Note>, onClick: (Note) -> Unit) {
    LazyColumn() {
        items(list.count()) {
            NoteItem(note = list[it], modifier = Modifier.clickable { onClick.invoke(list[it]) })
        }
    }
}
@Composable
private fun NoteItem(modifier: Modifier = Modifier, note: Note) {
    Text(text = note.text, modifier = modifier.padding(Dp(16f)))
}