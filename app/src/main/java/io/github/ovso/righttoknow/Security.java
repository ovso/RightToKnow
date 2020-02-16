package io.github.ovso.righttoknow;

import lombok.Getter;

@Getter public enum Security {
  GOOGLE_API_KEY("AIzaSyBdY9vP4_vQs5YEGJ3Ghu6s5gGY8yFlo0s"),
  NAVER_CLIENT_ID("HLSg0Vn_L6Y2S65DbzKP"),
  NAVER_CLIENT_SECRET("oa1k9kyE0E"),
  ADMOB_APP_ID(
      App.DEBUG ?
          "ca-app-pub-3940256099942544~3347511713" :
          "ca-app-pub-8679189423397017~9361379422"),
  ADMOB_UNIT_ID(
      App.DEBUG ?
          "ca-app-pub-3940256099942544/6300978111" :
          "ca-app-pub-8679189423397017/8758744192"),

  ADMOB_INTERSTITIAL_UNIT_ID(
      App.DEBUG ?
          "ca-app-pub-3940256099942544/1033173712" :
          "ca-app-pub-8679189423397017/2384988367");
  private String value;

  Security(String value) {
    this.value = value;
  }
}