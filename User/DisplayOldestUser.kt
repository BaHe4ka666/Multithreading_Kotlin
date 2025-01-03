package User

import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class DisplayOldestUser {


    fun show() {

        val textArea = JTextArea().apply {
            isEditable = false
            font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
            margin = Insets(32, 32, 32, 32)
        }

        val scrollPane = JScrollPane(textArea)

        JFrame().apply {
            isVisible = true
            size = Dimension(800, 600)
            isResizable = false
            add(scrollPane)
        }

        /* Регистрация (добавление объекта в коллекцию) наблюдателя, передавая текущий объект. */
        UserRepository.getInstance("qwerty").oldestUser.registerObserver { user ->
            textArea.text = "Oldest person is: $user"
        }
    }
}
