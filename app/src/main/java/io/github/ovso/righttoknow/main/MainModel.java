package io.github.ovso.righttoknow.main;

import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

/**
 * Created by jaeho on 2017. 8. 22..
 */

class MainModel {

  public Notices getNotices() {
    final Notices notices = new Notices();
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

    notices.addNotice(new Notice("TedPermission", "https://github.com/ParkSangGwon/TedPermission",
        "Copyright 2017 Ted Park", new ApacheSoftwareLicense20()));

    notices.addNotice(new Notice("AndroidPdfViewer", "https://github.com/barteksc/AndroidPdfViewer",
        "Copyright 2016 Bartosz Schiller", new ApacheSoftwareLicense20()));

    return notices;
  }
}
