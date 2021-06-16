package cz.levinzonr.roxie.sample.domain

import kotlinx.coroutines.delay

class GetNotesInteractor {


     suspend fun invoke(): List<Note> {
        delay(3000)
        return NoteRepository.loadAll()
    }
}

class DeleteNoteInteractor() {
    suspend fun invoke(input: Note) : Long  {
        NoteRepository.delete(input)
        return input.id
    }
}


class AddNoteInteractor() {
    data class Input(val note: String)
    var input: Input? = null
    suspend fun invoke(): Note {
        val note = NoteRepository.addNote(requireNotNull(input?.note))
        return note
    }
}