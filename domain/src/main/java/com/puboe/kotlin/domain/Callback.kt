package com.puboe.kotlin.domain

abstract class Callback

class Success<R>(val result: R) : Callback()

abstract class Failure(val message: String) : Callback()
class NetworkFailure : Failure("Network connection failed")
class ServerFailure : Failure("Server failure, please try again later.")
class ClientFailure : Failure("Data fetching failed")
