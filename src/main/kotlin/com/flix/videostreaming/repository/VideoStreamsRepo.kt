package com.flix.videostreaming.repository

import com.flix.videostreaming.entity.VideoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface VideoStreamsRepo :JpaRepository<VideoEntity,Long>{

}