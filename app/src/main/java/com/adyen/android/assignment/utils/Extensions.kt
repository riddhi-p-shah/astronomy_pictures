package com.adyen.android.assignment.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * It provides a function to format a LocalDate object as a string in the "MM/dd/yyyy" format.
 * I chose extension function because of convinience and reusability of this method
 */
fun LocalDate.formatAsMMDDYYYY(): String {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return this.format(formatter)
}
