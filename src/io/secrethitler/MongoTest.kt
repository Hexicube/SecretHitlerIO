package io.secrethitler

import com.mongodb.client.MongoDatabase
import org.bson.types.ObjectId
import org.litote.kmongo.*
import java.util.*

private var database: MongoDatabase? = null
var db: MongoDatabase?
        get() = database
        set(_) {}

fun main(args: Array<String>) {
    val client = KMongo.createClient("localhost", 15726)
    database = client.getDatabase("secret-hitler-app")

    val dataLists = db!!.getCollection<BannedIP>("bannedips")
    println(dataLists.countDocuments())

    val types: HashMap<String, Int> = HashMap()
    dataLists.find().forEach { data -> run {
        if (types.containsKey(data.type)) types[data.type] = types[data.type]!! + 1
        else types[data.type] = 1
        data.ip = ""
        data.save()
    }}
    types.forEach { type -> run {
        println("${type.key}: ${type.value}")
    }}

    val chatLists = db!!.getCollection<GeneralChat>("generalchats")
    println(chatLists.countDocuments())

    val roles: HashMap<String, Int> = HashMap()
    chatLists.find().forEach { data -> run {
        data.chats.forEach { chat -> run {
            if (chat.staffRole != null) {
                if (roles.containsKey(chat.staffRole)) roles[chat.staffRole] = roles[chat.staffRole]!! + 1
                else roles[chat.staffRole] = 1
            }
        }}
    }}
    roles.forEach { role -> run{
        println("${role.key}: ${role.value}")
    }}

    val reportList = db!!.getCollection<PlayerReport>("playerreports")
    println(reportList.countDocuments())
}

data class BannedIP (val _id: ObjectId, val bannedDate: Date, val type: String, var ip: String) {
    fun save() {
        db!!.getCollection<BannedIP>("bannedips").updateOneById(_id, this)
    }
}

data class ChatData (val chat: String, val userName: String, val staffRole: String?)
data class GeneralChat (val _id: ObjectId, val chats: List<ChatData>) {
    fun save() {
        db!!.getCollection<GeneralChat>("generalchats").updateOneById(_id, this)
    }
}

data class PlayerReport (val _id: ObjectId,
                         val date: Date, val gameUid: String, val reportingPlayer: String,
                         val reportedPlayer: String, val reason: String, val comment: String) {
    fun save() {
        db!!.getCollection<PlayerReport>("playerreports").updateOneById(_id, this)
    }
}