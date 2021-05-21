package com.flix.videostreaming.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class VideoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,
    val videoFileName:String,
    val videoEncoding:String,
    val fileSize:Long,
    val fileURL:String
)
