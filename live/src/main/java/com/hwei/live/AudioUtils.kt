package com.hwei.live

import android.media.MediaCodecInfo

object AudioUtils {

    // 添加7字节的ADTS头信息,buf是已经加过7字节头
    fun addADTS(buf: ByteArray) {
        //开始 :   12位0xFFF表示开始
        // ID  :  1 位  0: MPEG-4，1: MPEG-2   (这里取1)
        //Layer:  2 位  00
        // protection_absent:1位： 1代表没有CRC校验，0代表有 （取1）
        // 后三个合在一起也就是1001 -> 9，所以前两个byte是0xFFF9
        buf[0] = 0xFF.toByte()
        buf[1] = 0xF9.toByte()

        // profile： 2位  aac的类型，在codec设置的KEY_AAC_PROFILE
        // sampling_frequency_index: 4位 采样率下标，44100对应0x4
        // private_bit: 1位 取0
        // channel_configuration: 3位 通道数，010对应立体双声道
        val profile = MediaCodecInfo.CodecProfileLevel.AACObjectLC
        val chanCfg = 2
        val packetLen = buf.size
        val sampleRateInHz = 44100

        buf[2] = (((profile - 1 shl 6) + (sampleRateInHz shl 2) + (chanCfg shr 2)).toByte())
        buf[3] = ((chanCfg and 3 shl 6) + (packetLen shr 11)).toByte()
        buf[4] = (packetLen and 0x7FF shr 3).toByte()
        buf[5] = ((packetLen and 7 shl 5) + 0x1F).toByte()
        buf[6] = 0xFC.toByte()
    }
}