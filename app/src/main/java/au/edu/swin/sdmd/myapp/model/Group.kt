package au.edu.swin.sdmd.myapp.model

import java.time.LocalDateTime

data class Group(
    val id: Int,
    val name: String,
    val location: String,
    val type: String,
    val dateTime: LocalDateTime,
    val icon: Int = android.R.drawable.picture_frame
)