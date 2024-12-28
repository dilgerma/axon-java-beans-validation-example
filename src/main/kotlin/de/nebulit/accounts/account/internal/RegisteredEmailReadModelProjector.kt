package de.nebulit.accounts.account.internal

import org.axonframework.eventhandling.EventHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import de.nebulit.events.AccountRegisteredEvent
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.axonframework.config.ProcessingGroup

@Entity
class RegisteredAccount {
    @Id
    @Column(name = "email")
    var email: String? = null;

    @Column(name = "personId")
    var personId: String? = null;
}

interface RegisteredAccountReadModelRepository : JpaRepository<RegisteredAccount, String> {
    fun existsByPersonId(personId: String): Boolean
    fun existsByEmail(email: String): Boolean
}

/*
Boardlink: https://miro.com/app/board/uXjVLzp_Xps=/?moveToWidget=3458764611849271889
*/
@ProcessingGroup("validation")
@Component
class RegisteredEmailReadModelProjector(
    var repository: RegisteredAccountReadModelRepository
) {


    @EventHandler
    fun on(event: AccountRegisteredEvent) {
        //throws exception if not available (adjust logic)
        val entity = this.repository.findById(event.personId).orElse(RegisteredAccount())
        entity.apply {
            email = event.email
            personId = event.personId
        }.also { this.repository.save(it) }
    }

}
