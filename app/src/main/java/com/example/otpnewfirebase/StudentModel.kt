package com.example.otpnewfirebase

data class StudentModel(

var id:String?=null,
val educations:ArrayList<Educations>
)
data class Educations(
    val collage_address:String?=null,
    val collage_name:String?=null,
    val education_name:String?=null,


    )

