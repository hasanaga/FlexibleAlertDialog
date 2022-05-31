package info.hasanaga.flexiblealertdialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


class FlexibleAlertDialog : Fragment() {

    var lifecycleBlockReceiver: FlexibleAlertDialogInitEvent = {}
    var lifecycleBlock: Event = Event()

    var event: Event = Event()
    var mCustomEvent: CustomEvent = {}

    @IdRes
    var parent: Int = 0
    var mFragmentManager: FragmentManager? = null
    var mCancelable: Boolean = true

    lateinit var titleTextView: TextView
    lateinit var messageTextView: TextView
    lateinit var okButton: Button
    lateinit var cancelButton: Button
    lateinit var content: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleBlock.apply(lifecycleBlockReceiver)

        lifecycleBlock.onCreate.invoke()

        mCustomEvent(event, this)
        event.onCreate.invoke()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.flexible_alert_dialog_layout, container, false)

        titleTextView = view.findViewById(R.id.title)
        messageTextView = view.findViewById(R.id.message)
        okButton = view.findViewById(R.id.ok_button)
        cancelButton = view.findViewById(R.id.cancel_button)
        content = view.findViewById(R.id.content)


        if(mCancelable){
            view.findViewById<View>(R.id.outside_view).setOnClickListener { dismiss() }
        }

        lifecycleBlock.onCreateView(view)

        event.onCreateView(view)


        return view
    }

    override fun onResume() {
        super.onResume()
        event.onResume();
    }


    override fun onPause() {
        super.onPause()
        event.onPause();
    }

    override fun onDestroy() {
        event.onDestroy();
        super.onDestroy()
    }

    fun dismiss() {
        try {
            mFragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun show(){


        if(parent == -1){
            throw IllegalArgumentException("The Parent FrameLayout should be set.")
        }

        if(mFragmentManager == null){
            throw IllegalArgumentException("The FragmentManager should be set.")
        }

        try {
            mFragmentManager?.beginTransaction()?.replace(parent, this, "flexible_alert_dialog")?.commit()
        }catch (e: Exception){
            e.printStackTrace();
        }

        //childFragmentManager.beginTransaction().add(parent, this).commit()
    }


    fun setTitle(mTitle: CharSequence) {

        titleTextView.visibility = View.VISIBLE
        titleTextView.text = mTitle
    }

    fun setMessage(mMessage: CharSequence)  {

        messageTextView.text = mMessage
        messageTextView.visibility = View.VISIBLE
    }

    fun setPositiveButton(mText: CharSequence? = null, listener: OnClickListener?){

        okButton.visibility = View.VISIBLE
        okButton.setOnClickListener {

            event.onClickButton(ButtonType.PositiveButton, view)
            listener?.invoke(this)
        }
        mText?.let { okButton.text = it }
    }

    fun setNegativeButton(mText: CharSequence? = null, listener: OnClickListener?){

        cancelButton.visibility = View.VISIBLE
        cancelButton.setOnClickListener {

            event.onClickButton(ButtonType.NegativeButton, view)
            listener?.invoke(this)
        }

        mText?.let { cancelButton.text = it }
    }

    fun setView(layoutResId: Int) {

        layoutInflater.inflate(layoutResId, null).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            content.addView(this)
        }
    }

    fun setView(view: View) {
        view.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        content.addView(view)
    }

    class Builder(context: Context){

        private var params: Params = Params().apply { mContext = context }

        init {
            params.mContext = context
        }

        fun setTitle(@StringRes titleId: Int) = apply{ params.mTitle = params.mContext?.getString(titleId) }
        fun setTitle(title: CharSequence) = apply { params.mTitle = title }

        fun setMessage(@StringRes messageId: Int) = apply{ params.mMessage = params.mContext?.getString(messageId) }
        fun setMessage(message: CharSequence) = apply { params.mMessage = message }

        fun setPositiveButton(text: CharSequence? = null, onClickListener: OnClickListener) = apply {
            params.mPositiveButtonListener = onClickListener
            params.mPositiveButtonText = text
        }

        fun setNegativeButton(text: CharSequence? = null, onClickListener: OnClickListener) = apply {
            params.mNegativeButtonListener = onClickListener
            params.mNegativeButtonText = text
        }

        fun setCancelable(cancelable: Boolean) = apply { params.mCancelable = cancelable }

        fun setOnCancelListener(listener: OnClickListener) = apply { params.mCancelListener = listener }

        fun setOnDismissListener(listener: OnClickListener) = apply { params.mDismissListener = listener }

        fun setView(layoutResId: Int) = apply { params.mViewLayoutResId = layoutResId; params.mView = null }

        fun setView(view: View) = apply { params.mView = view; params.mViewLayoutResId = null }

        fun setParent(@IdRes parentId: Int) = apply { params.mParentId = parentId }

        fun setFragmentManager(fragmentManager: FragmentManager) = apply { params.mFragmentManager = fragmentManager }

        fun setEvent(event: CustomEvent) = apply { params.mCustomEvent = event }

        private fun create() = FlexibleAlertDialog().apply {

            this.mFragmentManager = params.mFragmentManager
            this.parent = params.mParentId ?: -1;

            params.mCustomEvent?.let { this.mCustomEvent = it }

            this.lifecycleBlockReceiver = {

                onCreateView = {
                    params.apply(this@apply)
                }

                onCreate = {
                    params.applyBeforeCreate(this@apply)
                }
            }
        }

        fun show() = create().apply { show() }
    }


    class Params{

        var mContext: Context? = null
        var mTitle: CharSequence? = null
        var mMessage: CharSequence? = null
        var mPositiveButtonText: CharSequence? = null
        var mPositiveButtonListener: OnClickListener? = null
        var mNegativeButtonText: CharSequence? = null
        var mNegativeButtonListener: OnClickListener? = null
        var mCancelable: Boolean = true
        var mCancelListener: OnClickListener? = null
        var mDismissListener: OnClickListener? = null
        var mViewLayoutResId: Int? = null
        var mView: View? = null
        var mCustomEvent: CustomEvent? = null
        @IdRes
        var mParentId: Int? = null
        var mFragmentManager: FragmentManager? = null

        fun applyBeforeCreate(dialog: FlexibleAlertDialog){
            mCustomEvent?.let { dialog.mCustomEvent = it }
        }

        fun apply(dialog: FlexibleAlertDialog){

            mTitle?.let { dialog.setTitle(it) }
            mMessage?.let { dialog.setMessage(it) }
            mViewLayoutResId?.let { dialog.setView(it) }
            mView?.let { dialog.setView(it) }
            mParentId?.let { dialog.parent = it }
            mCancelable.let { dialog.mCancelable = it }
            mFragmentManager?.let { dialog.mFragmentManager = it }

            if(mPositiveButtonText != null || mPositiveButtonListener != null){
                dialog.setPositiveButton(mPositiveButtonText, mPositiveButtonListener)
            }

            if(mNegativeButtonText != null || mNegativeButtonListener != null){
                dialog.setNegativeButton(mNegativeButtonText, mNegativeButtonListener)
            }
        }
    }

    enum class ButtonType{
        PositiveButton, NegativeButton
    }

    class Event {
        var onCreateView: (View) -> Unit = {}
        var onCreate: () -> Unit = {}
        var onPause: () -> Unit = {}
        var onDestroy: () -> Unit = {}
        var onResume: () -> Unit = {}
        var onClickButton: (button: ButtonType, view: View?) -> Unit = { _, _ ->}
    }
}


typealias FlexibleAlertDialogInitEvent = FlexibleAlertDialog.Event.() -> Unit
typealias CustomEvent = FlexibleAlertDialog.Event.(FlexibleAlertDialog) -> Unit
typealias OnClickListener = (FlexibleAlertDialog) -> Unit

//class LifecycleBlock{
//    var onCreateView: (View) -> Unit = {}
//    var onCreate: () -> Unit = {}
//}
