import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory
import java.text.DecimalFormat

class CurrencyConverterController {
    @FXML
    private val fromCurrency: ChoiceBox<String>? = null

    @FXML
    private val toCurrency: ChoiceBox<String>? = null

    @FXML
    private val amountSpinner: Spinner<Double>? = null

    @FXML
    private val resultLabel: Label? = null

    private val exchangeRates: MutableMap<String, Double> = HashMap()
    private val decimalFormat = DecimalFormat("#.##")

    fun initialize() {

        exchangeRates["GEL"] = 1.0
        exchangeRates["USD"] = 2.70
        exchangeRates["GBP"] = 3.30
        exchangeRates["EUR"] = 2.90


        fromCurrency!!.items = FXCollections.observableArrayList(exchangeRates.keys)
        toCurrency!!.items = FXCollections.observableArrayList(exchangeRates.keys)


        fromCurrency.value = "GEL"
        toCurrency.value = "USD"


        val valueFactory: SpinnerValueFactory<Double> = DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE, 1.0)
        amountSpinner!!.valueFactory = valueFactory
        amountSpinner.isEditable = true


        amountSpinner.valueProperty()
            .addListener { observable: ObservableValue<out Double>?, oldValue: Double?, newValue: Double? -> convertCurrency() }


        fromCurrency.valueProperty()
            .addListener { observable: ObservableValue<out String>?, oldValue: String?, newValue: String? -> convertCurrency() }
        toCurrency.valueProperty()
            .addListener { observable: ObservableValue<out String>?, oldValue: String?, newValue: String? -> convertCurrency() }





    }

    private fun convertCurrency() {
        val from = fromCurrency!!.value
        val to = toCurrency!!.value
        val amount = amountSpinner!!.value


        if (from != null && to != null && amount != null) {
            if (exchangeRates.containsKey(from) && exchangeRates.containsKey(to)) {
                val fromRate = exchangeRates[from]!!
                val toRate = exchangeRates[to]!!
                val result = (amount / fromRate) * toRate
                resultLabel!!.text = String.format(
                    "%s %s არის %s %s",
                    decimalFormat.format(amount),
                    from,
                    decimalFormat.format(result),
                    to
                )
            } else {
                resultLabel!!.text = "არასწორი ვალუტაა არჩეული!"
            }
        } else {
            resultLabel!!.text = "გთხოვთ, აირჩიოთ ვალუტა და შეიყვანოთ თანხა."
        }
    }
}
