package com.mitchmele.seeker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SeekerApplication

fun main(args: Array<String>) {
	runApplication<SeekerApplication>(*args)
}
