
package cz.levinzonr.roxie.sample.domain


/**
 * Normally we would implement a repository interface which is injected in the Domain layer
 */
object NoteRepository {

    private var idCounter = 4L

    private val notes = mutableListOf(
        Note(1, "note1"),
        Note(2, "note2"),
        Note(3, "note3")
    )


    fun addNote(title: String) : Note {
        val note = Note(idCounter, title)
        idCounter++
        notes.add(note)
        return note
    }

    fun loadAll(): List<Note> = notes.toList()

    fun findById(id: Long): Note? = notes.firstOrNull { it.id == id }

    fun delete(note: Note): Boolean = notes.remove(note)
}