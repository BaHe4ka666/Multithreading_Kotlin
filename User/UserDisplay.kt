package User

import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class UserDisplay {

    private val textArea = JTextArea().apply {
        isEditable = false
        font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        margin = Insets(32, 32, 32, 32)
    }

    fun show() {
        val scrollPane = JScrollPane(textArea)
        JFrame().apply {
            isVisible = true
            size = Dimension(800, 600)
            isResizable = false
            add(scrollPane)
        }

        /* Регистрация (добавление объекта в коллекцию) наблюдателя, передавая текущий объект. */
        UserRepository.getInstance("qwerty").registerObserver(this)
    }

    /* Обновление информации на экране */
    fun onChanged(users: List<User>) {
        users.joinToString("\n").also { textArea.text = it }
    }
}