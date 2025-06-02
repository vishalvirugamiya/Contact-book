package com.example.calldial

import java.io.Serializable

data class Contact_Data(
    var id: Int,
    var name: String,
    var phone: String,
    var SName:String,
    var email:String,
    var address:String,
    var birth:String,
    var gender:String
) :Serializable
