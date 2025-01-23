import okio.ByteString
import java.io.InputStream

class ByteStringInputStream(private val byteString: ByteString) : InputStream() {

    private var position = 0
    private var markPosition = -1 // 用于保存标记位置

    override fun read(): Int {
        return if (position < byteString.size) {
            byteString[position++].toInt() and 0xFF
        } else {
            -1 // End of stream
        }
    }

    override fun read(buffer: ByteArray, off: Int, len: Int): Int {
        if (position >= byteString.size) {
            return -1 // End of stream
        }

        val bytesToRead = len.coerceAtMost(byteString.size - position)
        // 直接使用 ByteString 的 copyInto
        byteString.copyInto(position, buffer, off, bytesToRead)
        position += bytesToRead
        return bytesToRead
    }

    // 标记当前位置，之后可以调用 reset() 恢复到该位置
    override fun mark(readlimit: Int) {
        // 我们不关心 readlimit 这里，因此直接标记当前位置
        markPosition = position
    }

    // 恢复到之前标记的位置
    override fun reset() {
        if (markPosition == -1) {
            throw IllegalStateException("Mark not set")
        }
        position = markPosition
    }

    // 判断流是否支持 mark/reset 操作
    override fun markSupported(): Boolean {
        return true
    }

    // 重写 available 方法，返回当前位置到 byteString 末尾的数据量
    override fun available(): Int {
        return byteString.size - position
    }
}
