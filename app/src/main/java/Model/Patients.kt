package Model

data class Patients(
    val name: String,
    val lastName: String,
    val age: Int,
    val disease: String,
    val roomNumber: Int,
    val bedNumber: Int,
    val medication: String,
    val addmissionDate: String,
    val medicationTime: String
)
