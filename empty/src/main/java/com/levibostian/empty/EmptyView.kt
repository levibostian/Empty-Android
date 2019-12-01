package com.levibostian.empty

import android.widget.LinearLayout
import android.content.Context
import android.util.AttributeSet
import android.annotation.TargetApi
import android.os.Build.VERSION_CODES
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView

class EmptyView: LinearLayout, View.OnClickListener {

    interface Listener {
        fun buttonPressed(id: String)
    }

    private lateinit var titleTextView: TextView
    private lateinit var messageTextView: TextView

    private var buttonStyle: Int = R.style.EmptyView_Button

    var listener: Listener? = null

    var title: String? = null
        set(value) {
            field = value

            titleTextView.apply {
                text = value
                visibility = if (field != null) View.VISIBLE else View.GONE
            }
        }

    var message: String? = null
        set(value) {
            field = value

            messageTextView.apply {
                text = value
                visibility = if (field != null) View.VISIBLE else View.GONE
            }
        }

    private var buttons: MutableMap<String, Button> = mutableMapOf()

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.emptyview_style)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        initialize(context, attrs, defStyleAttr)
    }
    @TargetApi(VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes) {
        initialize(context, attrs, defStyleAttr)
    }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
            setGravity(Gravity.CENTER)
        }
        setPadding(20, 20, 20, 20)

        val a = context.obtainStyledAttributes(attrs, R.styleable.EmptyView, defStyleAttr, 0)
        val stylesArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyViewStyle, defStyleAttr, 0)
        try {
            val titleText: String? = a.getString(R.styleable.EmptyView_empty_title_text)
            val messageText: String? = a.getString(R.styleable.EmptyView_empty_message_text)

            val titleStyle = stylesArray.getResourceId(R.styleable.EmptyViewStyle_emptyview_titleTextView, R.style.EmptyView_TitleTextView)
            val messageStyle = stylesArray.getResourceId(R.styleable.EmptyViewStyle_emptyview_messageTextView, R.style.EmptyView_MessageTextView)
            buttonStyle = stylesArray.getResourceId(R.styleable.EmptyViewStyle_emptyview_button, R.style.EmptyView_Button)

            val textViewLayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            titleTextView = TextView(ContextThemeWrapper(context, titleStyle), null, titleStyle)
            addView(titleTextView, textViewLayoutParams)

            messageTextView = TextView(ContextThemeWrapper(context, messageStyle), null, messageStyle)
            addView(messageTextView, textViewLayoutParams)

            title = titleText
            message = messageText
        } finally {
            a.recycle()
            stylesArray.recycle()
        }
    }

    fun addButton(id: String, message: String, style: Int? = null) {
        removeButton(id)

        val buttonStyle = style ?: this.buttonStyle

        val newButton = Button(ContextThemeWrapper(context, buttonStyle), null, buttonStyle).apply {
            tag = id
            text = message
            setOnClickListener(this@EmptyView)
        }
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(6, 6, 6, 6)
        }

        addView(newButton, layoutParams)

        buttons[id] = newButton
    }

    fun removeButton(id: String) {
        buttons[id] ?: return

        findViewWithTag<Button>(id)?.let { removeView(it) }
        buttons.remove(id)
    }

    override fun onClick(v: View?) {
        val clickedButton = v as? Button ?: return
        val buttonTag = clickedButton.tag as? String ?: return
        buttons[buttonTag] ?: return

        listener?.buttonPressed(buttonTag)
    }

}
