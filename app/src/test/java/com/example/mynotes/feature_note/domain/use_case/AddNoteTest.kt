package com.example.mynotes.feature_note.domain.use_case

import com.example.mynotes.feature_note.data.repository.FakeNoteRepository
import com.example.mynotes.feature_note.domain.model.InvalidNoteException
import com.example.mynotes.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteTest {

    private lateinit var addNote: AddNote
    private lateinit var getNote: GetNote
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        addNote = AddNote(fakeRepository)
        getNote = GetNote(fakeRepository)
    }

    @Test(expected = InvalidNoteException::class)
    fun `Add note with blank title, throws exception`() = runBlocking {
        val note = Note(1, "", "This is note content", System.currentTimeMillis())
        addNote(note)
        assertThat(note.id?.let { getNote(it)?.content }).isEqualTo("This is note content")
    }

    @Test(expected = InvalidNoteException::class)
    fun `Add note with blank content, throws exception`() = runBlocking {
        val note = Note(1, "This is note title", "", System.currentTimeMillis())
        addNote(note)
        assertThat(note.id?.let { getNote(it)?.title }).isEqualTo("This is note title")
    }

    @Test
    fun `Add note with correct data, note saved`() = runBlocking {
        val note = Note(1, "This is note title", "This is note content", System.currentTimeMillis())
        addNote(note)
        assertThat(note.id?.let { getNote(it)?.title }).isEqualTo("This is note title")
    }
}