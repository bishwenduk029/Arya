package api

interface ISerializer<T> {

    fun serialize(intents: T)
}