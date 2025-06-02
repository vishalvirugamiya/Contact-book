package com.example.calldial

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBeshHelper(context: Context) : SQLiteOpenHelper(context, "NewContact", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        var query="create table contact (id integer PRIMARY KEY AUTOINCREMENT, name Text, phone Text,SName Text,email Text,address Text,birth Text,gender Text)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun addCotact(name: String, phone: String, SName: String, email: String, address: String, birth: String, gender: String,
    ) {
         var insertQuary ="insert into contact (name,phone,SName,email,address,birth,gender) values('$name','$phone','$SName','$email','$address','$birth','$gender')"
         writableDatabase.execSQL(insertQuary)
    }

    fun allContactData() :ArrayList<Contact_Data> {

        var SelactQuary = "select * from contact"
        var cursor :Cursor = writableDatabase.rawQuery(SelactQuary,null)

        var list=ArrayList<Contact_Data>(0)

        while (cursor.moveToNext()){

            var idColIndex =cursor.getColumnIndex("id")
            var nameColIndex =cursor.getColumnIndex("name")
            var  phoneColIndex =cursor.getColumnIndex("phone")
            var  SnameColIndex =cursor.getColumnIndex("SName")
            var  emailColIndex =cursor.getColumnIndex("email")
            var  addressColIndex =cursor.getColumnIndex("address")
            var  birthColIndex =cursor.getColumnIndex("birth")
            var  genderColIndex =cursor.getColumnIndex("gender")

            var id:Int= cursor.getInt(idColIndex)
            var name = cursor.getString(nameColIndex)
            var phone = cursor.getString(phoneColIndex)
            var Sname = cursor.getString(SnameColIndex)
            var email = cursor.getString(emailColIndex)
            var address =cursor.getString(addressColIndex)
            var birth   =cursor.getString(birthColIndex)
            var gender  :String = cursor.getString(genderColIndex)

            var datta: Contact_Data = Contact_Data(id,name,phone,Sname,email,address,birth,gender)
            list.add(datta)
        }
        cursor.close()

        return list
    }
  fun DattaUser(Userid: Int): Contact_Data? {
      var SelectQuary = "select * from contact where id = $Userid "
      var cursor :Cursor = writableDatabase.rawQuery(SelectQuary,null)

      if(cursor.moveToNext())
      {

          var idColIndex =cursor.getColumnIndex("id")
          var nameColIndex =cursor.getColumnIndex("name")
          var  phoneColIndex =cursor.getColumnIndex("phone")
          var  SnameColIndex =cursor.getColumnIndex("SName")
          var  emailColIndex =cursor.getColumnIndex("email")
          var  addressColIndex =cursor.getColumnIndex("address")
          var  birthColIndex =cursor.getColumnIndex("birth")
          var  genderColIndex =cursor.getColumnIndex("gender")

          var id:Int= cursor.getInt(idColIndex)
          var name = cursor.getString(nameColIndex)
          var phone = cursor.getString(phoneColIndex)
          var Sname = cursor.getString(SnameColIndex)
          var email = cursor.getString(emailColIndex)
          var address =cursor.getString(addressColIndex)
          var birth   =cursor.getString(birthColIndex)
          var gender  :String = cursor.getString(genderColIndex)

          var datta: Contact_Data = Contact_Data(id,name,phone,Sname,email,address,birth,gender)
          return datta
      }
      cursor.close()
      return null
  }

    fun UpdateData(
        newName: String, newsureName: String,
        newMobleNum: String, newEmail: String,
        newAddress: String, newBirth: String, gender: String, id: Int
    )
    {
        var Query: String= "Update contact set name='$newName',SName ='$newsureName',phone='$newMobleNum',email='$newEmail',address='$newAddress',birth='$newBirth',gender='$gender'where id =$id"
        writableDatabase.execSQL(Query)
    }

    fun DeletData( id: Int)
    {
         var deleteQuery = "delete from contact where id = $id"
        writableDatabase.execSQL(deleteQuery)
    }
}