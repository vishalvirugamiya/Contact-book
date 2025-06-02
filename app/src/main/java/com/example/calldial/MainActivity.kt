package com.example.calldial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calldial.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var  BinDing : ActivityMainBinding
     lateinit var  listView: ListView


    lateinit var list: ArrayList<Contact_Data>
    lateinit var display :ArrayList<Contact_Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        BinDing=ActivityMainBinding.inflate(layoutInflater)
        setContentView(BinDing.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        listView=findViewById(R.id.ListView)

        BinDing.addBTN.setOnClickListener {
            var inten = Intent(this@MainActivity,NewContact::class.java)
            startActivity(inten)
        }

        newContact()
        AAdapter()

        BinDing.serchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return  false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                    filterData(newText)
                return  false
            }
        })

    }

    private fun filterData(Searchtext :String){
        display.clear()

        for (model in list){

            if(model.name.plus(model.SName).lowercase().contains(Searchtext.lowercase()))
            {
                display.add(model)
            }else if(model.phone.contains(Searchtext))
            {
                display.add(model)
            }

        }
        AAdapter()
    }

    private fun newContact() {

        var db:DataBeshHelper=DataBeshHelper(this)
         list = db.allContactData()

        display = ArrayList()
        display.addAll(list)



        registerForContextMenu(BinDing.ListView)

        BinDing.ListView.setOnItemClickListener { parent, view, position, id ->
            var intent : Intent=Intent(this@MainActivity,Cont_Details::class.java)
            intent.putExtra("id",list.get(position).id)
            startActivity(intent)
        }

        AAdapter()

    }

private fun AAdapter(){

    var adapter = Contact_Adapter(this,display)
    BinDing.ListView.adapter=adapter
}
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.contact_menu,menu)
        super.onCreateContextMenu(menu, v, menuInfo)

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var info = item.menuInfo as AdapterContextMenuInfo

        if(item.itemId==R.id.Delete_contact)
        {
            deletedata(list[info.position].id)

        }else {
         ///   Toast.makeText(this,item.title,Toast.LENGTH_SHORT).show()
        }
        return super.onContextItemSelected(item)
    }

    private fun deletedata(id:Int) {
        var db :DataBeshHelper = DataBeshHelper(this)
        db.DeletData(id)
        newContact()
    }


    override fun onBackPressed():Unit {
        super.onBackPressed()
        //Log.d("CDA", "onBackPressed Called")
        val setIntent = Intent(Intent.ACTION_MAIN)
        setIntent.addCategory(Intent.CATEGORY_HOME)
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(setIntent)

        return
    }

}