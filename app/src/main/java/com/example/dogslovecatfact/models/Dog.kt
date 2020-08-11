package com.example.dogslovecatfact.models

import com.google.gson.annotations.SerializedName

class Dog: Pet() {
    @SerializedName("name") var breed: String = "'"
}