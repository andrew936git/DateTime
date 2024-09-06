package com.example.datetime

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val GALLERY_REQUEST = 302
    var photoUri: Uri? = null

    private lateinit var imageVieIV: ImageView
    private lateinit var nameEditTextET: EditText
    private lateinit var surnameEditTextET: EditText
    private lateinit var dateEditTextET: EditText
    private lateinit var phoneEditTextET: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        imageVieIV = findViewById(R.id.imageViewIV)
        imageVieIV.setImageResource(R.drawable.person_plug)

        imageVieIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }
        nameEditTextET = findViewById(R.id.nameEditTextET)
        surnameEditTextET = findViewById(R.id.surnameEditTextET)
        dateEditTextET = findViewById(R.id.dateEditTextET)
        phoneEditTextET = findViewById(R.id.phoneEditTextET)
        saveButton = findViewById(R.id.saveButtonBT)
        saveButton.setOnClickListener{
            val person =  Person(
                nameEditTextET.text.toString(),
                surnameEditTextET.text.toString(),
                phoneEditTextET.text.toString(),
                dateEditTextET.text.toString(),
                photoUri.toString()
            )
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("person", person)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageVieIV = findViewById(R.id.imageViewIV)
        when(requestCode){
            GALLERY_REQUEST -> {
                if (resultCode === RESULT_OK){
                    photoUri = data?.data
                    imageVieIV.setImageURI(photoUri)
                }
            }
        }
    }

}