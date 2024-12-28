package de.nebulit.accounts.account.internal

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import mu.KotlinLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import de.nebulit.accounts.domain.commands.account.RegisterAccountCommand


import java.util.concurrent.CompletableFuture


data class AccountPayload(	var email:String,
	var personId:String,
	var name:String)

/*
Boardlink: https://miro.com/app/board/uXjVLzp_Xps=/?moveToWidget=3458764611846953302
*/
@RestController
class RegisterAccountRessource(private var commandGateway: CommandGateway) {

    var logger = KotlinLogging.logger {}

    
    @CrossOrigin
    @PostMapping("/debug/account")
    fun processDebugCommand(@RequestParam email:String,
	@RequestParam personId:String,
	@RequestParam name:String):CompletableFuture<Any> {
        return commandGateway.send(RegisterAccountCommand(email,
	personId,
	name))
    }
    

    
       @CrossOrigin
       @PostMapping("/account/{id}")
    fun processCommand(
        @PathVariable("id") aggregateId: java.util.UUID,
        @RequestBody payload: AccountPayload
    ):CompletableFuture<Any> {
         return commandGateway.send(RegisterAccountCommand(			email=payload.email,
			personId=payload.personId,
			name=payload.name))
        }
       

}
