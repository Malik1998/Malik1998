package com.example.androidabbyy

import android.Manifest
import android.R.attr.bitmap
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.view.CameraView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.util.*


class CameraActivity : AppCompatActivity(), ImageCapture.OnImageSavedListener {

    companion object {
        val FROM_CAMERA = "FROM CAMERA"
    }

    private lateinit var cameraView: CameraView
    private lateinit var takePictureButton: View
    private var CAMERA_REQUEST_CODE = 0
    private var STORAGE_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("cam", "onCreate")
        setContentView(R.layout.camera_fragment)
        Log.e("cam", "create")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED  && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED)
        {
            cameraView = findViewById(R.id.cameraView)
            takePictureButton = findViewById(R.id.takePictureButton)
                Log.e("cam", "camereeee")
                startCamera()
        } else {
            Log.e("cam", "request")
            requestPermission()
        }
    }


    private fun requestPermission() {
        Log.e("cam", "requested")
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_REQUEST_CODE ) {
            if (grantResults.size == 2 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.CAMERA
                    )
                ) {
                    Toast.makeText(this,"Please give permission to app", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"Please give permission to app in settings", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onImageSaved(file: File) {
        var list = getString(R.string.lorep_ipsum).toList().toMutableList()
        list.shuffle()
        var text = list.joinToString("")

        val detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
        val image = FirebaseVisionImage.fromFilePath(this, Uri.fromFile(file))
        val result = detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                text = firebaseVisionText.text
                if (text.isEmpty()) {
                    text = "Not recognized"
                }
                val id = MainActivity.getNoteRepository()!!
                    .create(Note(10, Date(), text, file.path))

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra(FROM_CAMERA, id)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                text = "Some error occured"
                finish()
            }

    }

    override fun onError(
        imageCaptureError: ImageCapture.ImageCaptureError,
        message: String,
        cause: Throwable?
    ) {
        finish()
    }

    private fun startCamera() {
        Log.e("cam", "startCamera")
        cameraView = findViewById(R.id.cameraView)
        takePictureButton = findViewById(R.id.takePictureButton)
        cameraView.captureMode = CameraView.CaptureMode.IMAGE
        cameraView.bindToLifecycle(this as LifecycleOwner)

        takePictureButton.setOnClickListener {
            cameraView.takePicture(
                generatePictureFile(),
                AsyncTask.SERIAL_EXECUTOR,
                this
            )
        }
    }

    private fun generatePictureFile(): File {
        val file = File(filesDir, UUID.randomUUID().toString())
        file.createNewFile()
        return file
    }
}
