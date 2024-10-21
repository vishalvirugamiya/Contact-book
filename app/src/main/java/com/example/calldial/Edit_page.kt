package com.example.calldial

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.calldial.databinding.ActivityEditPageBinding
import java.util.Calendar

class Edit_page : AppCompatActivity() {

    lateinit var binding: ActivityEditPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_edit_page)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

            binding= ActivityEditPageBinding.inflate(layoutInflater)
            setContentView(binding.root)

        var Data : Contact_Data=intent.getSerializableExtra("data") as Contact_Data


        binding.EdFName.setText(Data.name)
        binding.EdSureName.setText(Data.SName)
        binding.EDMobileNum.setText(Data.phone)
        binding.EDEmail.setText(Data.email)
        binding.EDaddress.setText(Data.address)
        binding.EDbirth.setText(Data.birth)


        if(Data.gender=="male")
        {
           binding.EDmale.isChecked=true
        }else if(Data.gender=="female"){
            binding.EDfemale.isChecked=true
        }
        binding.EDbirth.setOnClickListener {

            val c: Calendar = Calendar.getInstance()
            val mYear: Int = c.get(Calendar.YEAR) // current year
            val mMonth: Int = c.get(Calendar.MONTH) // current month
            val mDay: Int = c.get(Calendar.DAY_OF_MONTH) // current day

            // date picker dialog
            var datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth -> // set day of month , month and year value in the edit text
                    binding.EDbirth.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()

        }

        binding.cancel.setOnClickListener {

            var intent :Intent = Intent(this@Edit_page,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.submit.setOnClickListener {

            var newName :String= binding.EdFName.text.toString()
            var newsureName :String=binding.EdSureName.text.toString()
            var newMobleNum :String= binding.EDMobileNum.text.toString()
            var newEmail:String=binding.EDEmail.text.toString()
            var newAddress :String= binding.EDaddress.text.toString()
            var newBirth :String= binding.EDbirth.text.toString()

            var gender = ""

            if (binding.EDmale.isChecked) {
                gender = "male"
            } else if (binding.EDfemale.isChecked) {
                gender = "female"
            }


            val NumberPattern = Regex("[0-9]{10}")
            val emailPattern = Regex("[a-z]+[a-z0-9]+@[gmail]+\\.+[com]+")

            if(newEmail.matches(emailPattern) && newMobleNum.matches(NumberPattern)
                && newName!="" && newsureName!="" && newAddress!="" && newBirth!="")
            {
                var db :DataBeshHelper = DataBeshHelper(this)
                db.UpdateData(newName,newsureName,newMobleNum,newEmail,newAddress,newBirth,gender,Data.id)

                var intent:Intent=Intent(this@Edit_page,MainActivity::class.java)
                startActivity(intent)
            }else if(newName=="") {
                binding.EdFName.setError("Invalid name")
            }else if(newsureName=="") {
                binding.EdSureName.setError("Invalid sureNeme")
            }else if(!newMobleNum.matches(NumberPattern))
            {
                binding.EDMobileNum.setError("Invalid Number")
            }else if(newEmail.matches(emailPattern))
            {
                binding.EDEmail.setError("Invalid Email")
            }else if(newAddress=="")
            {
                binding.EDaddress.setError("Invalid Address")
            }else if(newBirth=="")
            {
                binding.EDbirth.setError("Invalid Birth")
            }

        }


    }

}