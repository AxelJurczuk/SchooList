package com.example.android.schoolist.extensions

import android.view.View

fun View.show (){
    visibility= View.VISIBLE
}

fun View.hide (){
    visibility= View.INVISIBLE
}

fun View.remove (){
    visibility= View.GONE
}