package com.abyte.daggerandroid.model


interface IUser

data class User(val userName: String, val age: Int) : IUser