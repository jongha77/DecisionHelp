package com.example.decisionhelp.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class SignupRequest(
    val id: String,
    val password: String
)

data class IdCheckRequest(val id: String)

data class LoginRequest(
    val id: String,
    val password: String
)

data class VoterRequest(
    val voterCode: String,
    val voterTitle: String,
    val voterDetail: String,
    val voterDate: String,
    val voterTime: String,
    val voterWhether: Boolean,
    val id: String,
)

data class VoterThemeRequest(
    val voterCode: String,
    val voterItemDetail: String
)

@Parcelize
data class Voter(
    val voterCode: String,
    val voterTitle: String,
    val voterDetail: String,
    val voterDate: String,
    val voterTime: String,
    val voterWhether: Int,
    val id: String,
) : Parcelable


