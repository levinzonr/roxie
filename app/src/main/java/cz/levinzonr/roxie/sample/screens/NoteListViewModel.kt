package cz.levinzonr.roxie.sample.screens

import cz.levinzonr.roxie.RoxieViewModel
import cz.levinzonr.roxie.Reducer
import cz.levinzonr.roxie.sample.domain.AddNoteInteractor
import cz.levinzonr.roxie.sample.domain.DeleteNoteInteractor
import cz.levinzonr.roxie.sample.domain.GetNotesInteractor
import cz.levinzonr.roxie.sample.domain.Note
import kotlinx.coroutines.flow.*

class NoteListViewModel() : RoxieViewModel<Action, State, Change>() {

    private val addNoteInteractor =  AddNoteInteractor()
    private val deleteNoteInteractor = DeleteNoteInteractor()
    private val loadNoteListUseCase = GetNotesInteractor()
    override val initialState =  State(isIdle = true)


    override val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isIdle = false,
                isLoading = true,
                isError = false
            )
            is Change.Notes -> state.copy(
                isLoading = false,
                notes = change.notes
            )
            is Change.Error -> state.copy(
                isLoading = false,
                isError = true
            )
            is Change.NoteDeleted -> state.copy(
                isLoading = false,
                notes = state.notes.filterNot { it.id == change.index }
            )
            is Change.NoteAdded -> state.copy(
                notes = state.notes.toMutableList().apply { add(change.note) }
            )
        }
    }

    init {
        startActionsObserver()
        dispatch(Action.LoadNotes)
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when(action) {
            is Action.LoadNotes -> bindGetNotesAction()
            is Action.DeleteNote -> bindDeleteNoteInteractor(action.note)
            is Action.AddNote -> bindAddNoteInteractor(action.text)
        }
    }



    private fun bindGetNotesAction(): Flow<Change> = flow {
        emit(Change.Loading)
        emit(Change.Notes(loadNoteListUseCase.invoke()))
    }

    private fun bindDeleteNoteInteractor(note: Note) : Flow<Change> = flow {
        emit(Change.Loading)
        emit(Change.NoteDeleted(deleteNoteInteractor.invoke(note)))
    }

    private fun bindAddNoteInteractor(text: String) : Flow<Change> = flow {
        addNoteInteractor.input = AddNoteInteractor.Input(text)
        emit(Change.NoteAdded(addNoteInteractor.invoke()))
    }
}
