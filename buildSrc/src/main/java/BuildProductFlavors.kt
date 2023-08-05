import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.LibraryProductFlavor
import org.gradle.api.NamedDomainObjectContainer

/**
 * Created by DatPT.
 */
object FlavorDimensions {
    const val ENVIRONMENT = "environment"
}

const val FLAVOR_PRODUCTION = "pro"
const val FLAVOR_DEV = "dev"

fun NamedDomainObjectContainer<ApplicationProductFlavor>.createApplicationFlavor(
    production: (ApplicationProductFlavor.() -> Unit)? = null,
    dev: (ApplicationProductFlavor.() -> Unit)? = null
) {

    create(FLAVOR_PRODUCTION) {
        dimension = FlavorDimensions.ENVIRONMENT
        matchingFallbacks.add(FLAVOR_PRODUCTION)
        production?.invoke(this)
    }

    create(FLAVOR_DEV) {
        dimension = FlavorDimensions.ENVIRONMENT
        applicationIdSuffix = ".$FLAVOR_DEV"
        versionNameSuffix = "-$FLAVOR_DEV"
        matchingFallbacks.add(FLAVOR_DEV)
        dev?.invoke(this)
    }
}

fun NamedDomainObjectContainer<LibraryProductFlavor>.createLibraryFlavor(
    production: (LibraryProductFlavor.() -> Unit)? = null,
    dev: (LibraryProductFlavor.() -> Unit)? = null
) {
    create(FLAVOR_PRODUCTION) {
        dimension = FlavorDimensions.ENVIRONMENT
        production?.invoke(this)
    }

    create(FLAVOR_DEV) {
        dimension = FlavorDimensions.ENVIRONMENT
        dev?.invoke(this)
    }
}