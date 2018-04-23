package io.github.ovso.righttoknow.framework.network.model;

import java.util.List;
import lombok.Getter;

/**
 * Created by jaeho on 2018. 3. 15
 */

@Getter public class GoogleGeocode {
  private String status;
  private List<GoogleGeocodeResult> results;
}
