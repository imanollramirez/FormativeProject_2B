package Model

data class Patients(
    val uuid: String,
    var name: String,
    var lastName: String,
    var age: Int,
    var disease: String,
    var roomNumber: Int,
    var bedNumber: Int,
    var medication: String,
    var addmissionDate: String,
    var medicationTime: String
)
