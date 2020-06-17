package com.ceh.fastfood.model.menu

data class Restaurant(
    val address: String,
    val created_at: String,
    val date_of_birth: Any,
    val gender: Any,
    val id: Int,
    val nrc_no: Any,
    val phone_no: String,
    val photo: String,
    val role: String,
    val township: TownshipX,
    val updated_at: String,
    val user: User
)