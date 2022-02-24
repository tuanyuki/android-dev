package com.example.chatkotlinv2.data

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class User(val username: String? = null, val uid: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}