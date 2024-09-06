package com.example.windowsPos2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class WindowsPos2Application

fun main(args: Array<String>) {
	runApplication<WindowsPos2Application>(*args)
}
