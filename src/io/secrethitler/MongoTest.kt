package io.secrethitler

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.types.ObjectId
import org.litote.kmongo.*
import java.util.*

val db: MongoDatabase = KMongo.createClient("localhost", 15726).getDatabase("secret-hitler-app")

fun main(args: Array<String>) {
    println(BannedIP.collection.countDocuments())
    // BannedIP(ObjectId(), Date(), "testban", "").save(true)
    val types: HashMap<String, Int> = HashMap()
    BannedIP.collection.find().forEach { data -> run {
        if (types.containsKey(data.type)) types[data.type] = types[data.type]!! + 1
        else types[data.type] = 1
    }}
    types.forEach { type -> run {
        println("${type.key}: ${type.value}")
    }}

    println(GeneralChat.collection.countDocuments())
    val roles: HashMap<String, Int> = HashMap()
    GeneralChat.collection.find().forEach { data -> run {
        data.chats.forEach { chat -> run {
            val role = chat.staffRole?:""
            if (roles.containsKey(chat.staffRole)) roles[role] = roles[role]!! + 1
            else roles[role] = 1
        }}
    }}
    roles.forEach { role -> run{
        println("${role.key}: ${role.value}")
    }}

    println(PlayerReport.collection.countDocuments())
}

data class BannedIP (val _id: ObjectId, val bannedDate: Date, val type: String, val ip: String) {
    companion object {
        val collection: MongoCollection<BannedIP> = db.getCollection<BannedIP>("bannedips")
    }
    fun save(insert: Boolean = false) {
        if (insert) collection.insertOne(this)
        else collection.updateOneById(_id, this)
    }
}

data class ChatData (val chat: String, val userName: String, val staffRole: String?)
data class GeneralChat (val _id: ObjectId, val chats: List<ChatData>) {
    companion object {
        val collection: MongoCollection<GeneralChat> = db.getCollection<GeneralChat>("generalchats")
    }
    fun save(insert: Boolean = false) {
        if (insert) collection.insertOne(this)
        else collection.updateOneById(_id, this)
    }
}

data class PlayerReport (val _id: ObjectId,
                         val date: Date, val gameUid: String, val reportingPlayer: String,
                         val reportedPlayer: String, val reason: String, val comment: String) {
    companion object {
        val collection: MongoCollection<PlayerReport> = db.getCollection<PlayerReport>("playerreports")
    }
    fun save(insert: Boolean = false) {
        if (insert) collection.insertOne(this)
        else collection.updateOneById(_id, this)
    }
}