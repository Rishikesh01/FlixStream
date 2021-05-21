package com.flix.videostreaming.web

import com.flix.videostreaming.entity.VideoEntity
import com.flix.videostreaming.repository.VideoStreamsRepo
import com.flix.videostreaming.services.VideoStreamService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@RestController
@RequestMapping("/")
class VideoStreamController(
    private val videoStreamsRepo: VideoStreamsRepo,
    private val videoStreamService: VideoStreamService
) {
    @GetMapping
    fun videosStreamsList(): Flux<ResponseEntity<VideoEntity>> {
        return videoStreamsRepo.findAll().toFlux().map { video -> ResponseEntity.ok(video) }
    }

    @GetMapping("/video/{id}")
    fun videoStream(
        @PathVariable(value = "id") id: Long,
        @RequestHeader(value = "Range", required = false) range: String?
    ): ResponseEntity<ByteArray> {

        val fileDetails: VideoEntity = videoStreamsRepo.findById(id).get()
        val header: HttpHeaders = HttpHeaders()
        header.set("Content-Type", "video/${fileDetails.videoEncoding}")
        header.set("Content-Length", "${fileDetails.fileSize}")
        header.set("Accept-Ranges", "bytes")
        header.set(
            "Content-Range",
            "bytes ${videoStreamService.startRange}-${fileDetails.fileSize - 1}/${fileDetails.fileSize}"
        )
        val arr:ByteArray = videoStreamService.videoStreamOutput(id,range)
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).headers(header).body(arr)
    }
    @PostMapping("/video")
    fun saveVideo(@RequestBody videoEntity: VideoEntity):String{
        videoStreamsRepo.save(videoEntity)
        return "saved"
    }
}