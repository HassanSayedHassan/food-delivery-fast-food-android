package com.ceh.fastfood.model.order

data class Orders(
    val created_at: String,
    val id: Int,
    val order_date: String,
    val total: String,
    val updated_at: String,
    val user_detail_id: String,
    val voucher_no: Long
)