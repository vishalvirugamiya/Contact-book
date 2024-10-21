package com.example.calldial

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.calldial.databinding.ContectItemBinding

class Contact_Adapter(var mainActivity: MainActivity, var list: ArrayList<Contact_Data>) :BaseAdapter() {
    override fun getCount(): Int {

        return list.size
    }

    override fun getItem(position: Int): Any {
      return position
    }

    override fun getItemId(position: Int): Long {
       return 0L
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var binding : ContectItemBinding = ContectItemBinding.inflate(mainActivity.layoutInflater,parent,false)

        var datta : Contact_Data = list[position]

        binding.AddContact.text=datta.name +" "+ datta.SName
        binding.Number.text=datta.phone

        if(datta.gender=="female")
        {
            binding.Gender.setImageResource(R.drawable.female)
        }else
        {
            binding.Gender.setImageResource(R.drawable.male)
        }

//        binding.click.setOnClickListener{
//            var intent = Intent(mainActivity,Cont_Details::class.java)
//            intent.putExtra("id",datta.id)
//            mainActivity.startActivity(intent)
//        }

        return binding.root
    }


}