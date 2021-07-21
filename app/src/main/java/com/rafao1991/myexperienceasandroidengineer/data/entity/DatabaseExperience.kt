package com.rafao1991.myexperienceasandroidengineer.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rafao1991.myexperienceasandroidengineer.model.Experience

@Entity(tableName = "experiences")
data class DatabaseExperience(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "description") val description: String
)

fun List<DatabaseExperience>.asDomainModel(): List<Experience> {
    return map {
        Experience(
            year = it.year,
            description = it.description)
    }
}