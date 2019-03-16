package itg8.com.nowzonedesigndemo.common;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.leakcanary.LeakCanary;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.util.ArrayList;
import java.util.List;

import itg8.com.nowzonedesigndemo.utility.model.breath.BreathingModel;
import itg8.com.nowzonedesigndemo.utility.model.breath.ItemDetail;
import timber.log.BuildConfig;
import timber.log.Timber;

import static itg8.com.nowzonedesigndemo.common.CommonMethod.SHARED;
import static timber.log.Timber.DebugTree;

@ReportsCrashes(formUri = "", mailTo = "app.itechgalaxy@gmail.com", mode = ReportingInteractionMode.SILENT)
public class AppApplication extends Application {

    private static final String PROFILE_MODEL = "PROFILE_MODEL";
    private static AppApplication mInstance;
    private ProfileModel profileModel;
    private BreathingModel mBreathModel;

    public static synchronized AppApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ACRA.init(this);
        mInstance.initPreference();
        mBreathModel = new BreathingModel();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    private void initPreference() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(MODE_PRIVATE)
                .setPrefsName(SHARED)
                .setUseDefaultSharedPreference(false)
                .build();
    }

    public ProfileModel getProfileModel() {
        String mProfileModel = SharePrefrancClass.getInstance(this).getPref(PROFILE_MODEL);
        if (profileModel == null && mProfileModel != null) {
            profileModel = new Gson().fromJson(mProfileModel, new TypeToken<ProfileModel>() {
            }.getType());
        }
        return profileModel;
    }

    public void setProfileModel(ProfileModel model) {
        if (model != null)
            SharePrefrancClass.getInstance(this).savePref(PROFILE_MODEL, new Gson().toJson(model));
    }

    public BreathingModel getBreathModel() {
        return mBreathModel;
    }


    public void resetBreathing() {
        mBreathModel = new BreathingModel();
    }

    public BreathingModel addBreathing(ItemDetail detail) {
        List<ItemDetail> details = mBreathModel.getItemDetails();
        if (details == null) {
            details = new ArrayList<>();
        }

        details.add(detail);
        mBreathModel.setItemDetails(details);
        return mBreathModel;
    }

    public void removeBreathing(ItemDetail detail) {
        List<ItemDetail> details = mBreathModel.getItemDetails();
        if (details == null) {
            details = new ArrayList<>();
        }
        if (details.contains(detail))
            details.remove(detail);
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
