package com.example.parsejsonretrofitgsonexample.arrayJSON

import com.google.gson.annotations.SerializedName

// Uncomment the following line if you're using the Kotlinx Serialization converter
// @Serializable
data class ArrayJSONModel(

    // @SerializedName(" ") for the Gson converter
    // @field:Json(name = " ") for the Moshi converter
    // @SerialName(" ") for the Kotlinx Serialization converter
    // @JsonProperty(" ") for the Jackson converter

    @SerializedName("id")
    var employeeId: String?,

    @SerializedName("employee_name")
    var employeeName: String?,

    @SerializedName("employee_salary")
    var employeeSalary: String?,

    @SerializedName("employee_age")
    var employeeAge: String?

)