package cz.levinzonr.roxie.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cz.levinzonr.roxie.sample.screens.NoteListViewModel
import cz.levinzonr.roxie.sample.screens.NotesScreen
import cz.levinzonr.roxie.sample.ui.theme.RoxieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = NoteListViewModel()
        super.onCreate(savedInstanceState)
        setContent {
            RoxieTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                   NotesScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RoxieTheme {
        Greeting("Android")
    }
}