package android.alex.edu.menuapp;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */

public class BackgroundFragment extends DialogFragment implements SeekBar.OnSeekBarChangeListener,
                                        TextWatcher,
                                        View.OnClickListener
{


    private SeekBar sbRed;
    private SeekBar sbGreen;
    private SeekBar sbBlue;
    private EditText etRed;
    private EditText etGreen;
    private EditText etBlue;
    private boolean byUser = true;
    private boolean userIsCurrentlyScrolling = false;

    private Button btnCancel;
    private Button btnOK;

    private onDialogFragmentChoocedColorListener listener;

    private TextView tvForFragmentInitText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_background, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(v, savedInstanceState);

        initLayout(v);

        initEvents(v);
    }

    private void initLayout(View v)
    {
        sbRed = (SeekBar) v.findViewById(R.id.sbRed);
        sbGreen = (SeekBar) v.findViewById(R.id.sbGreen);
        sbBlue = (SeekBar) v.findViewById(R.id.sbBlue);
        etRed = (EditText) v.findViewById(R.id.etRed);
        etBlue = (EditText) v.findViewById(R.id.etBlue);
        etGreen = (EditText) v.findViewById(R.id.etGreen);

        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnOK = (Button) v.findViewById(R.id.btnOK);
    }

    private void initEvents(View v) {
        sbRed.setOnSeekBarChangeListener(this);
        sbGreen.setOnSeekBarChangeListener(this);
        sbBlue.setOnSeekBarChangeListener(this);

        etGreen.addTextChangedListener(this);
        etBlue.addTextChangedListener(this);
        etRed.addTextChangedListener(this);

        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {

        this.userIsCurrentlyScrolling = fromUser;

        if (userIsCurrentlyScrolling) {
            etGreen.setText(String.valueOf(sbGreen.getProgress()));
            etBlue.setText(String.valueOf(sbBlue.getProgress()));
            etRed.setText(String.valueOf(sbRed.getProgress()));
        }

        this.userIsCurrentlyScrolling = false;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        try
        {
            int redValue = Integer.valueOf(etRed.getText().toString());
            int greenValue = Integer.valueOf(etGreen.getText().toString());
            int blueValue = Integer.valueOf(etBlue.getText().toString());

            if(!userIsCurrentlyScrolling)
            {
                sbRed.setProgress(redValue);
                sbGreen.setProgress(greenValue);
                sbBlue.setProgress(blueValue);
            }
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    public void onClick(View v)
    {
        if(v == btnOK)
        {
            int rgb = Color.rgb(sbRed.getProgress(),
                                sbGreen.getProgress(),
                                sbBlue.getProgress());

            listener.onDialogFragmentChoocedColor(rgb);
        }

        dismiss();
    }

    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(context instanceof onDialogFragmentChoocedColorListener)
        {
            listener = (onDialogFragmentChoocedColorListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + "Must implement "

                    + onDialogFragmentChoocedColorListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }



    public interface onDialogFragmentChoocedColorListener
    {
        void onDialogFragmentChoocedColor(int color);
    }



}

