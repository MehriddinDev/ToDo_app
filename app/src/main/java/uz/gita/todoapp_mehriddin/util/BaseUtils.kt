package uz.gita.todoapp_mehriddin.util

import android.content.Context
import android.util.Log
import android.widget.Toast

fun logger(m:String,tag:String = "TTT"){
    Log.d(tag, m)
}

fun toast(context:Context,m:String){
    Toast.makeText(context, m, Toast.LENGTH_SHORT).show()
}