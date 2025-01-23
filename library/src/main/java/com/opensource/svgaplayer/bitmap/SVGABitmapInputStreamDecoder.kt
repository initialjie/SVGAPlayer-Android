package com.opensource.svgaplayer.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream

/**
 * 通过 InputStream 解码 Bitmap
 *
 */
internal object SVGABitmapInputStreamDecoder : SVGABitmapDecoder<InputStream>() {

    override fun onDecode(data: InputStream, ops: BitmapFactory.Options): Bitmap? {
        return BitmapFactory.decodeStream(data, null, ops)
    }
}