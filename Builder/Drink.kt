package Builder

data class Drink(
    val type: String,
    val additives: List<String>,
    val diningOption: String,
    val temperature: String
) {
    class Builder() {
        private var type: String = "Coffee"
        private var additives: List<String> = listOf()
        private var diningOption: String = "To go"
        private var temperature: String = "Hot"

        fun type(type: String): Builder {
            this.type = type
            return this
        }

        fun additives(additives: List<String>): Builder {
            this.additives = additives
            return this
        }

        fun diningOption(diningOption: String): Builder {
            this.diningOption = diningOption
            return this
        }

        fun temperature(temperature: String): Builder {
            this.temperature = temperature
            return this
        }

        fun build(): Drink {
            return Drink(type, additives, diningOption, temperature)
        }
    }
}