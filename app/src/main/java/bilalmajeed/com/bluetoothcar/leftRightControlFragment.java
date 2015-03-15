package bilalmajeed.com.bluetoothcar;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.OutputStream;

public class LeftRightControlFragment extends Fragment{
    Button leftBtn, rightBtn;
    private final String TURN_RIGHT = "3";
    private final String TURN_LEFT = "4";
    private final String STOP = "0";

    LeftRightControlListener activityCommander;

    public interface LeftRightControlListener{
        public void getLRDirection(String direction); //get left right directions
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            activityCommander = (LeftRightControlListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_right_control_fragment, container, false);

        leftBtn = (Button) view.findViewById(R.id.leftBtn);
        rightBtn = (Button) view.findViewById(R.id.rightBtn);

        rightBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    buttonTouched(TURN_RIGHT);
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    buttonTouched(STOP);

                return true;
            }
        });

        leftBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    buttonTouched(TURN_LEFT);
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    buttonTouched(STOP);

                return true;
            }
        });

        return view;
    }

    public void buttonTouched(String direction){
        activityCommander.getLRDirection(direction);
    }
}
