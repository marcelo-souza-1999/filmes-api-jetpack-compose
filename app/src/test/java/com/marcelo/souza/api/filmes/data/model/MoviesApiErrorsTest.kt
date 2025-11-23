package com.marcelo.souza.api.filmes.data.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class MoviesApiErrorsTest {

    @Test
    fun `Network error stores IOException correctly`() {
        val ioException = IOException("network down")
        val error = MoviesApiErrors.Network(ioException)

        assertTrue(error is MoviesApiErrors.Network)
        assertEquals(ioException, error.ioException)
        assertEquals(ioException, error.cause)
    }

    @Test
    fun `Server error stores statusCode and body correctly`() {
        val error = MoviesApiErrors.Server(
            statusCode = 503,
            body = "service unavailable"
        )

        assertTrue(error is MoviesApiErrors.Server)
        assertEquals(503, error.statusCode)
        assertEquals("service unavailable", error.body)
        assertEquals(null, error.cause)
    }

    @Test
    fun `Error wraps original throwable correctly`() {
        val throwable = IllegalStateException("boom")
        val error = MoviesApiErrors.Error(throwable)

        assertTrue(error is MoviesApiErrors.Error)
        assertEquals(throwable, error.originalThrowable)
        assertEquals(throwable, error.cause)
    }
}