package com.levibostian.emptyexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.levibostian.empty.EmptyView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EmptyView.Listener {

    companion object {
        private const val HIDE_TITLE_BUTTON_ID = "HIDE_TITLE_BUTTON_ID"
        private const val HIDE_MESSAGE_BUTTON_ID = "HIDE_MESSAGE_BUTTON_ID"
        private const val ADD_BUTTON_BUTTON_ID = "ADD_BUTTON_BUTTON_ID"
        private const val REMOVE_BUTTON_BUTTON_ID = "REMOVE_BUTTON_BUTTON_ID"
        private const val UNIQUE_STYLE_BUTTON_ID = "UNIQUE_STYLE_BUTTON_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
    }

    private fun setupViews() {
        act_main_emptyview.addButton(HIDE_TITLE_BUTTON_ID, "Hide title")
        act_main_emptyview.addButton(HIDE_MESSAGE_BUTTON_ID, "Hide message")
        act_main_emptyview.addButton(ADD_BUTTON_BUTTON_ID, "Add button")
        act_main_emptyview.addButton(UNIQUE_STYLE_BUTTON_ID, "I am unique!", R.style.EmptyView_Button_Default)

        act_main_emptyview.listener = this
    }

    override fun buttonPressed(id: String) {
        when (id) {
            HIDE_TITLE_BUTTON_ID -> {
                act_main_emptyview.title = null
            }
            HIDE_MESSAGE_BUTTON_ID -> {
                act_main_emptyview.message = null
            }
            ADD_BUTTON_BUTTON_ID -> {
                act_main_emptyview.addButton(REMOVE_BUTTON_BUTTON_ID, "Remove me")
            }
            REMOVE_BUTTON_BUTTON_ID -> {
                act_main_emptyview.removeButton(REMOVE_BUTTON_BUTTON_ID)
            }
        }
    }

}
