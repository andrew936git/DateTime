package com.example.datetime

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class SecondActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var nameTextViewTV: TextView
    private lateinit var surnameTextViewTV: TextView
    private lateinit var phoneTextViewTV: TextView
    private lateinit var dateTextViewTV: TextView
    private lateinit var timerTextViewTV: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        init()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        title = "Данные о пользователе"
        setSupportActionBar(toolbar)
        val getImageViewIV: ImageView = findViewById(R.id.getImageViewIV)
        nameTextViewTV = findViewById(R.id.nameTextViewTV)
        surnameTextViewTV = findViewById(R.id.surnameTextViewTV)
        phoneTextViewTV = findViewById(R.id.phoneTextViewTV)
        dateTextViewTV = findViewById(R.id.dateTextViewTV)
        timerTextViewTV = findViewById(R.id.timerTextViewTV)

        val person = intent?.getParcelableExtra<Person>("person")
        nameTextViewTV.text = "${person?.name}"
        surnameTextViewTV.text = "${person?.surname}"
        phoneTextViewTV.text = "тел: ${person?.phone}"
        val date: String = person?.date.toString()
        getImageViewIV.setImageURI(Uri.parse(person?.image))
        ageCalculation(date)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_second, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.exit_menu -> {
                Toast.makeText(
                    applicationContext,
                    "Программа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun ageCalculation(date: String) {

        val dateNow = LocalDate.now()
        val formatDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatDayNow = dateNow.format(formatDate)

        var a = LocalDate.parse(date, formatDate)
        val b = LocalDate.parse(formatDayNow, formatDate)
        val age = Period.between(a, b)
        while (a.year <= b.year) {
            a = a.plusYears(1)
        }
        val period = Period.between(b, a)
        dateTextViewTV.text = "Возраст ${age.get(ChronoUnit.YEARS)}"
        if (period.get(ChronoUnit.MONTHS) == 0L) timerTextViewTV.text = "С днем рождения!!!"
        else timerTextViewTV.text = " До дня рождения осталось ${period.get(ChronoUnit.MONTHS)}" +
                " месяцев и ${period.get(ChronoUnit.DAYS)} дней"
    }
}

