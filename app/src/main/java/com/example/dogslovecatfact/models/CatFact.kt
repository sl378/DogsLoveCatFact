package com.example.dogslovecatfact.models

import com.google.gson.annotations.SerializedName

class CatFact: Pet() {
    @SerializedName("text") var fact: String = "'"
}