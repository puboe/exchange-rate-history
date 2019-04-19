package com.puboe.kotlin.domain

abstract class Callback

class Success<R>(val result: R) : Callback()

abstract class Failure : Callback()
class NetworkFailure : Failure()
class ServerFailure : Failure()
class ClientFailure : Failure()
