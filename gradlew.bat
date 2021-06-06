<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/rootView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/background">

    <include
        android:id="@+id/toolbar"
        layout="@layout/common_tool_bar_notificationwo" />

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/paymentImg"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:orientation="vertical"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHeight_percent=".65"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <androidx.constraintlayout.widget.ConstraintLayout
                android:id="@+id/paymentLL"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.2">

                <com.github.akashandroid90.imageletter.MaterialLetterIcon
                    android:id="@+id/payerImage"
                    android:layout_width="@dimen/common_60dp"
                    android:layout_height="@dimen/common_60dp"
                    android:scaleType="fitXY"
                    app:border_color="@color/White"
                    app:border_width="1dp"
                    app:initials="true"
                    app:initials_number="2"