package com.ceh.fastfood.model.menu

data class MenuX(
    val category: Category,
    val description: String,
    val id: Int,
    val menu_name: String,
    val menu_photo: String,
    val menu_price: String,
    val townships: List<Township>,
    val updated_at: String,
    val user_detail_id: UserDetailId
)