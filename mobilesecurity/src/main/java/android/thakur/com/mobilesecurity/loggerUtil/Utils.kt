package android.thakur.com.mobilesecurity.loggerUtil

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectOutput
import java.io.ObjectOutputStream
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SealedObject
import javax.crypto.spec.SecretKeySpec

class Utils {

    companion object {
        fun sealedObjectStream(value: SealedObject):ByteArray?{
            val bos  = ByteArrayOutputStream()
            val out: ObjectOutput?
            var yourBytes:ByteArray? = null
            try {
                out = ObjectOutputStream(bos)
                out.writeObject(value)
                out.flush()
                yourBytes = bos.toByteArray()

            } finally {
                try {
                    bos.close()
                } catch (ex: IOException) {
                    // ignore close exception
                }

                return yourBytes
            }
        }

        fun generateCipher(value:Long) : Cipher {
            var byte = (value%298570).toString().toByteArray()
            val sha = MessageDigest.getInstance("SHA-1")
            byte = sha.digest(byte)
            byte = Arrays.copyOf(byte, 32) // use only first 128 bit
            val key = SecretKeySpec(byte, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            return cipher
        }
    }
}