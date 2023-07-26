package com.example.ny.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sat_table")
class SATScores(@field:ColumnInfo(name = "dbn") @field:PrimaryKey var dbn: String) {

    @ColumnInfo(name = "school_name")
    var school_name = ""

    @ColumnInfo(name = "num_of_sat_test_takers")
    var num_of_sat_test_takers = ""

    @ColumnInfo(name = "sat_critical_reading_avg_score")
    var sat_critical_reading_avg_score = ""

    @ColumnInfo(name = "sat_math_avg_score")
    var sat_math_avg_score = ""

    @ColumnInfo(name = "sat_writing_avg_score")
    var sat_writing_avg_score = ""

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("No. of SAT Test Takers : ")
        sb.append(num_of_sat_test_takers)
        sb.append(System.getProperty("line.separator"))
        sb.append("SAT Critical Reading Average score : ")
        sb.append(sat_critical_reading_avg_score)
        sb.append(System.getProperty("line.separator"))
        sb.append("SAT Math Average score : ")
        sb.append(sat_math_avg_score)
        sb.append(System.getProperty("line.separator"))
        sb.append("SAT Writing Average score : ")
        sb.append(sat_writing_avg_score)
        return sb.toString()
    }
}
