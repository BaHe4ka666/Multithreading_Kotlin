package Dogs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dog(
    @SerialName("id") val id: Int,
    @SerialName("breed") val breed: String,
    @SerialName("name") val name: String,
    @SerialName("weight") val weight: Double
)
