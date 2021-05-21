package com.flix.videostreaming.services

import com.flix.videostreaming.repository.VideoStreamsRepo
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.BufferedInputStream
import java.io.File
import java.net.URL
import java.net.URLConnection

@Service
class VideoStreamService(private val videoStreamsRepo: VideoStreamsRepo) {
    companion object {
        const val BUFFER_SIZE: Int = 10240 * 5 * 100
    }

    var startRange: Long = 0

    fun videoStreamOutput(id: Long,range:String?): ByteArray {
        if (range!=null) startRange= range.split("-")[0].substring(6).toLong()
        val videoBytes: ByteArray = ByteArray(BUFFER_SIZE)
        val video: File = getFile(videoStreamsRepo.findById(id).get().fileURL)
        val fileURL: URL = getFileUrl(video)
        val con: URLConnection = fileURL.openConnection()
        val bufferedStream: BufferedInputStream = BufferedInputStream(con.getInputStream(), Companion.BUFFER_SIZE)
        bufferedStream.skip(startRange)
        bufferedStream.read(videoBytes,0, BUFFER_SIZE)
        return videoBytes
    }

    private fun getFile(filePath: String): File {
        return File(filePath)

    }

    private fun getFileUrl(file: File): URL {
        return file.toURI().toURL()
    }


}