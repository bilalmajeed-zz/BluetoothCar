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

public class UpDownControlFragment extends Fragment{
    Button upBtn, downBtn;
    private final String GO_FORWARDS = "1";
    private final String GO_BACKWARDS = "2";
    private final String STOP = "0";

    UpDownControlListener activityCommander;

    public interface UpDownControlListener{
        public void getUDDirection(String direction); //get up down directions
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            activityCommander = (UpDownControlListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.up_down_control_fragment, container, false);

        upBtn = (Button) view.findViewById(R.id.upBtn);
        downBtn = (Button) view.findViewById(R.id.downBtn);

        upBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    buttonTouched(GO_FORWARDS);
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    buttonTouched(STOP);

                return true;
            }
        });

        downBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    buttonTouched(GO_BACKWARDS);
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    buttonTouched(STOP);

                return true;
            }
        });

        return view;
    }

    public void buttonTouched(String direction){
        activityCommander.getUDDirection(direction);
    }
}
