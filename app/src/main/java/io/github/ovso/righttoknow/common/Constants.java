package io.github.ovso.righttoknow.common;

import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class Constants {
  public final static String BASE_URL = "http://info.childcare.go.kr";
  public final static String FAC_LIST_PATH_QUERY = "/info/cfvp/VioltfcltySlL.jsp?limit=500";
  public final static String VIOLATOR_LIST_PATH_QUERY = "/info/cfvp/VioltactorSlL.jsp?limit=500";
  public final static String CERTIFIED_LIST_PATH_QUERY =
      "/info/cera/community/notice/CertNoticeSlPL.jsp?limit=500";

  public final static int ITEM_VIOLATION_FACILITY = 0;
  public final static int ITEM_VIOLATOR = 1;
  public static final int ITEM_CERTIFIED = 2;
  public static final int ITEM_NEWS = 3;
  public static final int ITEM_VIDEO = 4;

  public static final String FCM_KEY_CONTENT_POSITION = "content_position";

  public final static int EMOJI_ABUSED = 0x1F62D;

  public static Notices getNotices() {
    final Notices notices = new Notices();

    notices.addNotice(new Notice("App Icon(Launcher Icon)", "https://edukame.com/",
        MyApplication.getInstance().getString(R.string.photo_credit_by), null));

    notices.addNotice(
        new Notice("MaterialSearchView", "https://github.com/MiguelCatalan/MaterialSearchView",
            "Copyright 2015 Miguel Catalan Bañuls", new ApacheSoftwareLicense20()));

    notices.addNotice(new Notice("ButterKnipe", "https://github.com/JakeWharton/butterknife",
        "Copyright 2013 Jake Wharton", new ApacheSoftwareLicense20()));

    notices.addNotice(new Notice("Lombok", "https://github.com/rzwitserloot/lombok",
        "Copyright (C) 2009-2015 The Project Lombok Authors.\n"
            + "\n"
            + "Permission is hereby granted, free of charge, to any person obtaining a copy\n"
            + "of this software and associated documentation files (the \"Software\"), to deal\n"
            + "in the Software without restriction, including without limitation the rights\n"
            + "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n"
            + "copies of the Software, and to permit persons to whom the Software is\n"
            + "furnished to do so, subject to the following conditions:\n"
            + "\n"
            + "The above copyright notice and this permission notice shall be included in\n"
            + "all copies or substantial portions of the Software.\n"
            + "\n"
            + "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n"
            + "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n"
            + "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n"
            + "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n"
            + "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n"
            + "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n"
            + "THE SOFTWARE.\n", null));

    notices.addNotice(new Notice("AndroidPdfViewer", "https://github.com/barteksc/AndroidPdfViewer",
        "Copyright 2016 Bartosz Schiller", new ApacheSoftwareLicense20()));
    notices.addNotice(
        new Notice("AVLoadingIndicatorView", "https://github.com/81813780/AVLoadingIndicatorView",
            "Copyright 2015 jack wang", new ApacheSoftwareLicense20()));
    notices.addNotice(new Notice("RxJava", "https://github.com/ReactiveX/RxJava",
        "Copyright (c) 2016-present, RxJava Contributors.", new ApacheSoftwareLicense20()));
    notices.addNotice(
        new Notice("Retrofit", "https://github.com/square/retrofit", "Copyright 2013 Square, Inc.",
            new ApacheSoftwareLicense20()));
    notices.addNotice(new Notice("Okhttp", "https://github.com/square/okhttp",
        "Licensed under the Apache License, Version 2.0 (the \"License\")",
        new ApacheSoftwareLicense20()));

    notices.addNotice(new Notice("RxFirebase", "https://github.com/kunny/RxFirebase",
        "Copyright 2016-2017 Taeho Kim <jyte82@gmail.com>", new ApacheSoftwareLicense20()));
    return notices;
  }
}
