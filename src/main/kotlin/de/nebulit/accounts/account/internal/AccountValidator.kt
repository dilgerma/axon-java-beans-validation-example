import de.nebulit.accounts.account.internal.RegisteredAccountReadModelRepository
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PersonIdValidator::class])
annotation class UniquePersonId(
    val message: String = "personId already exists",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueEmailValidator::class])
annotation class UniqueEmail(
    val message: String = "email already exists",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

@Component
class PersonIdValidator(
    @Autowired
    private val registeredAccountRepository: RegisteredAccountReadModelRepository
) : ConstraintValidator<UniquePersonId,String> {
    override fun isValid(personId: String?, context: ConstraintValidatorContext): Boolean {
        if (personId == null) {
            return true
        }
        return !registeredAccountRepository.existsByPersonId(personId)
    }
}

@Component
class UniqueEmailValidator(
    @Autowired
    private val registeredAccountRepository: RegisteredAccountReadModelRepository
) : ConstraintValidator<UniqueEmail,String> {
    override fun isValid(personId: String?, context: ConstraintValidatorContext): Boolean {
        if (personId == null) {
            return true
        }
        return !registeredAccountRepository.existsByEmail(personId)
    }
}