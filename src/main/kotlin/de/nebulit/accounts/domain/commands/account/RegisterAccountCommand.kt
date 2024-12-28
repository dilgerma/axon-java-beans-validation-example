package de.nebulit.accounts.domain.commands.account

import UniqueEmail
import UniquePersonId
import org.axonframework.modelling.command.TargetAggregateIdentifier
import de.nebulit.common.Command


/*
Boardlink: https://miro.com/app/board/uXjVLzp_Xps=/?moveToWidget=3458764611846953302
*/
data class RegisterAccountCommand(
	@TargetAggregateIdentifier
	@field:UniqueEmail
    var email:String,
	@field:UniquePersonId
	var personId:String,
	var name:String
): Command
