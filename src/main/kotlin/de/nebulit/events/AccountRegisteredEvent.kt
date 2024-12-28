package de.nebulit.events

import de.nebulit.common.Event



/*
Boardlink: https://miro.com/app/board/uXjVLzp_Xps=/?moveToWidget=3458764611846953103
*/
data class AccountRegisteredEvent(
    var email:String,
	var name:String,
	var personId:String
) : Event
