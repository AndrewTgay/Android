package com.udacity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates


class LoadingButton @JvmOverloads constructor(
    context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var process = 0f
    private var widthSize = 0
    private var heightSize = 0
    private var myBackground:Int = Color.BLUE
    private var myTextColor : Int = Color.BLACK
    private var text = context.getString(R.string.button_name)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style= Paint.Style.FILL
        textAlign= Paint.Align.CENTER
        textSize=55.0f
        typeface= Typeface.create("", Typeface.BOLD)
    }

    private val valueAnimator = ValueAnimator.ofFloat(0f,1f).apply {
        duration=2000

        addUpdateListener {
            process = it.animatedValue.toString().toFloat()
            invalidate()
        }

        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                this@LoadingButton.isEnabled = false
                buttonState = ButtonState.Loading
            }
            override fun onAnimationEnd(animation: Animator?) {
                this@LoadingButton.isEnabled = true
                buttonState = ButtonState.Completed
                process=0f
            }
        })
    }

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) {
            p, old, new ->
        when(new){
            ButtonState.Clicked ->{
                valueAnimator.start()
                invalidate()
            }
            ButtonState.Loading->{
                text = context.getString(R.string.button_loading)
                invalidate()
            }
            ButtonState.Completed->{
                valueAnimator.cancel()
                text = context.getString(R.string.button_name)
                invalidate()
            }
        }
    }
    init {
        isClickable = true
        val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0)
        try {
            myBackground = attr.getColor(
                R.styleable.LoadingButton_myBackground,
                ContextCompat.getColor(context, R.color.green)
            )

            myTextColor = attr.getColor(
                R.styleable.LoadingButton_myTextColor,
                ContextCompat.getColor(context, R.color.white)
            )
        } finally {
            attr.recycle()
        }
    }

 fun startTheButton(){
     contentDescription = text
     buttonState = ButtonState.Clicked
     invalidate()
 }




    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color= myBackground
        paint.alpha = 100
        canvas!!.drawRect(0f,0f,width.toFloat(),height.toFloat(),paint)
        Log.i("Loooog","button width $width , Hight $height")

        paint.color = Color.GREEN
        canvas!!.drawRect(0f,0f,width.toFloat()*process,height.toFloat(),paint)

        paint.color= Color.BLUE
        canvas.drawArc(0f, 0f, 60f , 60f , 0f,360f*process , true, paint)

        paint.color= myTextColor
        canvas.drawText(text , width/2f,height/2f,paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}