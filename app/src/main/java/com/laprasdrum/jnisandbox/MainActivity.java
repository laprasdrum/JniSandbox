package com.laprasdrum.jnisandbox;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.laprasdrum.jnisandbox.lib.NdkManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String TAG = "FragmentOnMain";

        @InjectView(R.id.text)
        TextView textView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.inject(this, rootView);

            Log.i(TAG, "Architecture: " + NdkManager.getInstance().stringFromJNI());
            int sum = NdkManager.getInstance().addTwoFromJNI(2, 3);
            Log.i(TAG, "2 + 3: " + sum);

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    Log.i(TAG, "|   " + NdkManager.getInstance().countFromJNI() + " counted ASYNC. will be shown with SYNC"); // No block to Main Thread
                    return null;
                }
            }.execute(null, null, null);
            Log.i(TAG, "|   " + NdkManager.getInstance().countFromJNI() + " counted SYNC.  will be shown with ASYNC."); // Block to Main Thread

            textView.setText(NdkManager.getInstance().stringFromJNI());
            Log.i(TAG, NdkManager.getInstance().countFromJNI() + " counted SYNC. will be shown after indent area"); // Block to Main Thread

            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.reset(this);
        }
    }
}
