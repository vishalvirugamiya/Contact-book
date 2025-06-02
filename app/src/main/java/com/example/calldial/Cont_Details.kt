package com.example.calldial

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calldial.databinding.ActivityContDetailsBinding

class Cont_Details : AppCompatActivity(){

    lateinit var binding:ActivityContDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityContDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        binding.toappbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
//
//            if(item.itemId==R.id.copy_contact)
//            {
//                Toast.makeText(this,"Delete Contact",Toast.LENGTH_SHORT).show()
//            }else if(item.itemId==R.id.shareContact)
//            {
//                Toast.makeText(this,"share Contact",Toast.LENGTH_SHORT).show()
//            }
//            return@OnMenuItemClickListener false
//        })

        var id =intent.getIntExtra("id",0)

        var db:DataBeshHelper=DataBeshHelper(this)

        var Datta =db.DattaUser(id)


        binding.name.text= "Name : "+ Datta!!.name +" "+ Datta.SName
        binding.MobileNum.text = Datta.phone
        binding.gmail.text="Email : "+Datta.email
        binding.address.text="Address : "+Datta.address
        binding.birth.text="Birth : "+Datta.birth
        binding.gender.text="Gender : "+Datta.gender

        binding.MobileNum.setOnClickListener {

            var menu :PopupMenu=PopupMenu(this@Cont_Details,binding.MobileNum)
            menuInflater.inflate(R.menu.popup_menu,menu.menu)

            menu.setOnMenuItemClickListener { item->

                    val clipboard :ClipboardManager=getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("copy",binding.MobileNum.text)
                    clipboard.setPrimaryClip(clip)
                return@setOnMenuItemClickListener false
            }
            menu.show()
        }

        if(Datta.gender=="female")
        {
            binding.genderMF.setImageResource(R.drawable.female)
        }else
        {
            binding.genderMF.setImageResource(R.drawable.male)
        }
        binding.editBT.setOnClickListener {

            var inten :Intent=Intent(this@Cont_Details,Edit_page::class.java)   //Edit Button
            inten.putExtra("data",Datta)
            startActivity(inten)
            finish()
        }

        binding.back.setOnClickListener {

            var intent = Intent(this@Cont_Details,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}