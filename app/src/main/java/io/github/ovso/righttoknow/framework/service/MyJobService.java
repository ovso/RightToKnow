package io.github.ovso.righttoknow.framework.service;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import hugo.weaving.DebugLog;

/**
 * Created by jaeho on 2017. 9. 14
 */

public class MyJobService extends JobService {
  @DebugLog @Override public boolean onStartJob(JobParameters job) {
    return false;
  }

  @DebugLog @Override public boolean onStopJob(JobParameters job) {
    return false;
  }
}
