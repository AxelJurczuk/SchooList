package com.example.android.schoolist.attendance

interface OnShowGroupListener {
    fun showGroupInfo(groupName:String)
    fun showGroupQuery (date:String, groupName:String)
}