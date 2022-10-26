package com.example.shoesappfwd.models

import com.udacity.shoestore.models.Shoe

class ShoeVM {
    var name: String? = null
    var size: String? = null
    var company: String? = null
    var description: String? = null
    var images: List<String>? = mutableListOf()

    constructor(name: String, size: String, company: String, description: String, images: List<String>?)
    {
        this.name = name
        this.images = images
        this.company = company
        this.size = size
        this.description = description
    }
    fun toShoe(): Shoe
    {
        return Shoe(this.name, this.size!!.toDouble(), this.company, this.description, this.images)
    }
}