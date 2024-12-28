package de.nebulit.domain

import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.CreationPolicy

import java.util.UUID

import de.nebulit.accounts.domain.commands.account.RegisterAccountCommand
import de.nebulit.events.AccountRegisteredEvent

@Aggregate
class AccountsAggregate {

    @AggregateIdentifier
    var aggregateId: String? = null


    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    @CommandHandler
    fun handle(command: RegisterAccountCommand) {

        AggregateLifecycle.apply(
            AccountRegisteredEvent(
                email = command.email,
                personId = command.personId,
                name = command.name
            )
        )

    }


    @EventSourcingHandler
    fun on(event: AccountRegisteredEvent) {
        // handle event
        this.aggregateId = event.email
    }


}
