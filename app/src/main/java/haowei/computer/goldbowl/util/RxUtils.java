package haowei.computer.goldbowl.util;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxUtils {

  public static <T> Single.Transformer<T, T> applySchedulers() {
    return tSingle -> tSingle.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

}
