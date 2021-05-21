package com.flix.videostreaming

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class VideoStreamingApplication

fun main(args: Array<String>) {
    runApplication<VideoStreamingApplication>(*args)
}
