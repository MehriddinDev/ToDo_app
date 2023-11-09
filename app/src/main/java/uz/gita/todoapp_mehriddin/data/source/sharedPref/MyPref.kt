package uz.gita.todoapp_mehriddin.data.source.sharedPref

import android.content.Context
import uz.gita.todoapp_mehriddin.app.App
import javax.inject.Inject

class MyPref @Inject constructor() {
    private val pref = App.context.getSharedPreferences("Product", Context.MODE_PRIVATE)
    private val edit = pref.edit()

    companion object {
        private lateinit var myPref: MyPref

        fun getInstance(): MyPref {
            if (!Companion::myPref.isInitialized) {
                myPref = MyPref()
            }
            return myPref
        }
    }




    fun saveFirst(email: Boolean) {
        edit.putBoolean("User", email).apply()
    }

    fun getFirst(): Boolean {
        return pref.getBoolean("User", true)
    }

    fun saveLastCategory(name:String){
        edit.putString("category",name).apply()
    }

    fun getLastCategory():String{
        return pref.getString("category","")!!
    }


}