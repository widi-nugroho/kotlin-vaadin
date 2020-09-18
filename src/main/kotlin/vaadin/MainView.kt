package vaadin

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.PWA
import java.time.LocalDate


class Motif(val nama: String){}
class Sprei(val warna: String){}
var motifs= mutableListOf(
        Motif("kaktus"),
        Motif("paris"),
        Motif("minions"),
        Motif("arsenal")
)
var spreis= mutableListOf(
        Sprei("hijau"),
        Sprei("biru"),
        Sprei("merah"),
        Sprei("coklat")
)
class kerjaan(val kerjaan: String){}
var pekerjaan= mutableListOf(
        kerjaan("guru"),
        kerjaan("pegawai"),
        kerjaan("pembalap")
)

@Route("")
@CssImport.Container(value = [  // repeatable annotations are not supported by Kotlin, please vote for https://youtrack.jetbrains.com/issue/KT-12794
    CssImport("./styles/shared-styles.css"),
    CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
])
class MainView : KComposite() {
    private lateinit var nameField: TextField
    private lateinit var tanggalPesanan:DatePicker
    private val now: LocalDate = LocalDate.now()

    private lateinit var listMotif: ComboBox<Motif>
    private lateinit var listSprei: ComboBox<Sprei>
    private lateinit var jumlahPesanan:IntegerField

    private lateinit var saveButton: Button
    private lateinit var dialogSubmit:Dialog
    private lateinit var submitButton: Button
    private lateinit var cancelButton: Button

    // The main view UI definition
    private val root = ui {
        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        verticalLayout(isPadding = true) {
            horizontalLayout(isPadding = true, true){
                nameField = textField("Nama")

                tanggalPesanan=datePicker("test") {
                    this.value=now
                }
            }
            horizontalLayout(isPadding = true) {
                listMotif=comboBox("Motif"){
                    this.setItemLabelGenerator(Motif::nama)
                    this.setItems(motifs)
                }
                listSprei=comboBox("Sprei"){
                    this.setItemLabelGenerator(Sprei::warna)
                    this.setItems(spreis)
                }
                jumlahPesanan=integerField("Jumlah")
            }
            dialogSubmit=dialog(){
                this.isCloseOnEsc=false
                this.isCloseOnOutsideClick=false
                submitButton=button("Submit")
                cancelButton=button("Cancel")
                var h1=horizontalLayout(true){
                    this.add(submitButton,cancelButton)
                }

            }
            saveButton = button("Submit order") {
                setPrimary(); addClickShortcut(Key.ENTER)
                this.horizontalAlignSelf
                this.addClickListener { event->dialogSubmit.open() }
            }
        }
    }

    init {
        // attach functionality to the UI components.
        // It's a good practice to keep UI functionality separated from UI definition.
        // Button click listeners can be defined as lambda expressions

        /*saveButton.onLeftClick {
            Notification.show("${nameField.value} memesan sprei warna ${listSprei.value.warna} dengan motif ${listMotif.value.nama} sebanyak ${jumlahPesanan.value} item pada ${tanggalPesanan.value}")
        }*/
        submitButton.onLeftClick {dialogSubmit.close()}
        submitButton.onLeftClick { Notification.show("${nameField.value} memesan sprei warna ${listSprei.value.warna} dengan motif ${listMotif.value.nama} sebanyak ${jumlahPesanan.value} item pada ${tanggalPesanan.value}") }
        cancelButton.onLeftClick {dialogSubmit.close()}

    }
}

@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
class AppShell: AppShellConfigurator
