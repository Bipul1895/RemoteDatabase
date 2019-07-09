//package in.droom.analyticslibrary;
//
//import android.os.Handler;
//
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.logging.LogRecord;
//
//public class CallAsyncTask {
//    final Handler handler=new Handler();
//    Timer timer=new Timer();
//    TimerTask doAsyncTask=new TimerTask() {
//        @Override
//        public void run() {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        BackgroundTask backgroundTask = new BackgroundTask();
//                        backgroundTask.execute();
//                    }catch (Exception e) {
//
//                    }
//                }
//            });
//        }
//    };
//
//    timer.sc
//}
