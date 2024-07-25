package com.example.poto_test

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.io.OutputStream

class standard_4  : AppCompatActivity() {

    private val SELECT_PICTURE = 1
    private val REQUEST_WRITE_STORAGE = 2
    private var currentImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.standard_4)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_STORAGE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            val inputStream = contentResolver.openInputStream(selectedImageUri!!)
            var bitmap = BitmapFactory.decodeStream(inputStream)

            // Determine which ImageView initiated the select image action
            when (currentImageView?.id) {
                R.id.photo1_1 -> {
                    bitmap = rotateImage(bitmap, -90) // Rotate left 90 degrees
                }

                R.id.photo1_2 -> {
                    bitmap = rotateImage(bitmap, -90) // Rotate left 90 degrees
                }

                R.id.photo1_3 -> {
                    bitmap = rotateImage(bitmap, -90) // Rotate left 90 degrees
                }
                R.id.photo2_1 -> {
                    bitmap = rotateImage(bitmap, 90) // Rotate right 90 degrees
                }
                R.id.photo2_2 -> {
                    bitmap = rotateImage(bitmap, 90) // Rotate right 90 degrees
                }
                R.id.photo2_3 -> {
                    bitmap = rotateImage(bitmap, 90) // Rotate right 90 degrees
                }
            }
            currentImageView?.setImageBitmap(bitmap)
        }
    }

    private fun rotateImage(source: Bitmap, angle: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    fun selectImage(view: android.view.View) {
        currentImageView = view as ImageView
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, SELECT_PICTURE)
    }


    fun downloadFrame(view: android.view.View) {
        val photoFrame = findViewById<LinearLayout>(R.id.photo_frame)
        val bitmap = getBitmapFromView(photoFrame)
        saveBitmapToGallery(bitmap)
    }

    private fun getBitmapFromView(view: android.view.View): Bitmap {
        val scaleFactor = 4 // 해상도를 높일 비율
        val width = view.width * scaleFactor
        val height = view.height * scaleFactor
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.scale(scaleFactor.toFloat(), scaleFactor.toFloat()) // 캔버스도 같은 비율로 스케일링
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmapToGallery(bitmap: Bitmap) {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "photo_frame_${System.currentTimeMillis()}.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val resolver = contentResolver
        var uri: Uri? = null

        try {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            uri = resolver.insert(contentUri, values)

            uri?.let {
                val outputStream: OutputStream? = resolver.openOutputStream(it)
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream) // 최상의 품질로 압축
                }
                outputStream?.close()
                Toast.makeText(this, "Saved to Gallery", Toast.LENGTH_LONG).show()
            } ?: throw IOException("Failed to create new MediaStore record.")
        } catch (e: IOException) {
            uri?.let { orphanUri -> resolver.delete(orphanUri, null, null) }
            e.printStackTrace()
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show()
        }
    }
}
