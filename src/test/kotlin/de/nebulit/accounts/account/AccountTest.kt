package de.nebulit.accounts.account

import de.nebulit.common.Event
import de.nebulit.common.support.RandomData
import de.nebulit.domain.AccountsAggregate
import de.nebulit.common.CommandException
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import de.nebulit.events.AccountRegisteredEvent
import de.nebulit.accounts.domain.commands.account.RegisterAccountCommand
import de.nebulit.common.support.BaseIntegrationTest
import de.nebulit.common.support.ProjectionFixtureConfiguration
import javax.validation.ValidatorFactory
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.interceptors.BeanValidationInterceptor
import org.axonframework.modelling.command.Repository
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension


/**


Boardlink: https://miro.com/app/board/uXjVLzp_Xps=/?moveToWidget=3458764611850281130
 */
class AccountTest: BaseIntegrationTest() {

    @Autowired
    private lateinit var repository: Repository<AccountsAggregate>
    @Autowired
    private lateinit var commandGateway: CommandGateway

    @Test
    fun `Account Test`() {

        val personId = "person1"
        val email = "email"

        var fixture =
            ProjectionFixtureConfiguration.aggregateInstance { repository.newInstance { AccountsAggregate() } }
        fixture
            .given(RandomData.newInstance<AccountRegisteredEvent> {
                this.email = email
            })
        fixture.apply()

        //WHEN
        val command = RegisterAccountCommand(
            // reuse existing email
            email = email,
            personId = RandomData.newInstance { },
            name = RandomData.newInstance { }
        )

        commandGateway.sendAndWait<Any>(command)


    }

}
