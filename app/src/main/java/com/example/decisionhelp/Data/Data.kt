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
    var isExpired: Boolean = true
) : Parcelable

data class VoterItem(
    val voterItemCode: Int,
    val voterCode: String,
    val voterItemDetail: String,
    var count: Int,
    var isChecked: Boolean // isChecked 속성 추가
)


data class VoterResult(
    val voterItemCode: Int,
    val id: String,
    var count: Int,
    var result: Boolean
)

data class itemResultRequest(
    val voterItemCode: Int,
    val id: String,
    var result: Boolean
)

data class voterCountRequest(
    val voterItemCode: Int,
    var count: Int
)

data class itemResultCheck(
    val voterItemCode: Int,
    var result: Int
)

data class voterClosedRequest(
    val voterCode: String,
    val voterDate: String,
    val voterTime: String
)




