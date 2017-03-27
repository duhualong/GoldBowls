package haowei.computer.goldbowl.core;

/**
 */
public interface ActionCallbackListener<T> {
  /**
   * 请求成功时调用
   *
   * @param data 返回的数据
   */
  void onActionSuccess(T data);

  /**
   * 请求失败时调用
   *
   * @param errorCode 错误码
   * @param message 错误信息
   */
  void onActionFailure(int errorCode, String message);
}
