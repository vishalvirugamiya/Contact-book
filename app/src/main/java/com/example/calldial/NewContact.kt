package com.example.calldial

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calldial.databinding.ActivityNewContactBinding
import java.util.Calendar
import java.util.jar.Attributes.Name


class NewContact : AppCompatActivity() {

    lateinit var binDing: ActivityNewContactBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()

        binDing = ActivityNewContactBinding.inflate(layoutInflater)
        setContentView(binDing.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binDing.cancel.setOnClickListener {
            var intent = Intent(this@NewContact, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        binDing.birth.setOnClickListener {

            val c: Calendar = Calendar.getInstance()
            val mYear: Int = c.get(Calendar.YEAR) // current year
            val mMonth: Int = c.get(Calendar.MONTH) // current month
            val mDay: Int = c.get(Calendar.DAY_OF_MONTH) // current day

            // date picker dialog
            var datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth -> // set day of month , month and year value in the edit text
                    binDing.birth.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()

        }


        binDing.submit.setOnClickListener {

            var Name  =  binDing.FName.text.toString()
            var number=  binDing.MobileNum.text.toString()
            var SName =  binDing.SureName.text.toString()
            var email =  binDing.Email.text.toString()
            var address= binDing.address.text.toString()
            var birth =  binDing.birth.text.toString()

            var gender = ""

            if (binDing.male.isChecked) {
                gender = "male"
            } else if (binDing.female.isChecked) {
                gender = "female"
            }

            val NumberPattern = Regex("[0-9]{10}")
            val emailPattern = Regex("[a-z]+[a-z0-9]+@[gmail]+\\.+[com]+")


//            if (email.matches(emailPattern)) {
//            //  Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT) .show();
//
//            } else {
//              //  Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
//            }


            if(email.matches(emailPattern) && Name!="" && number.matches(NumberPattern) && address!="" && birth!="" )
            {
                var db: DataBeshHelper = DataBeshHelper(this@NewContact)
                db.addCotact(Name, number, SName, email, address, birth, gender)

                var intan = Intent(this@NewContact, MainActivity::class.java)
                startActivity(intan)
                finish()

            }else if(Name=="") {
                binDing.FName.setError("Invalid Name")
                Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show()

            }else if(SName=="") {
                binDing.SureName.setError("Invalid Name")
                Toast.makeText(this, "Invalid SureName", Toast.LENGTH_SHORT).show()

            }else if(!number.matches(NumberPattern)){

                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show()
                binDing.MobileNum.setError("Invalid Name")

            }else if(!email.matches(emailPattern)){

                binDing.Email.setError("Invalid Email")
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            }else if(address==""){

                binDing.address.setError("Invalid Address")
                Toast.makeText(this, "Invalid Address", Toast.LENGTH_SHORT).show()
            }else if(birth==""){

                binDing.birth.setError("Invalid Birth")

                Toast.makeText(this, "Invalid Birth", Toast.LENGTH_SHORT).show()
            }


        }

   }


}